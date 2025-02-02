package com.ikservices.oficinamecanica.budgets.infra.controller;

import com.ikservices.oficinamecanica.budgets.application.BudgetBusinessConstant;
import com.ikservices.oficinamecanica.budgets.application.usecases.*;
import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.domain.BudgetStatusEnum;
import com.ikservices.oficinamecanica.budgets.infra.BudgetConverter;
import com.ikservices.oficinamecanica.budgets.infra.constants.BudgetConstant;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("budgets")
public class BudgetController {
	
	private static final Logger LOGGER = IKLoggerUtil.getLogger(BudgetController.class);
	
	@Autowired
	private Environment environment;
	@Autowired
	@Lazy
	private BudgetConverter converter;
	
	private final GetBudget getBudget;
	private final ListBudgets listBudgets;
	private final SaveBudget saveBudget;
	private final UpdateBudget updateBudget;
	private final ChangeStatus changeStatus;
	private final IncreaseAmount increaseAmount;
	private final DecreaseAmount decreaseAmount;

    private final CreateBudgetPDF createPDF;
	
	public BudgetController(GetBudget getBudget, ListBudgets listBudgets, SaveBudget saveBudget,
                            UpdateBudget updateBudget, ChangeStatus changeStatus, IncreaseAmount increaseAmount,
                            DecreaseAmount decreaseAmount, CreateBudgetPDF createPDF) {
		this.getBudget = getBudget;
		this.listBudgets = listBudgets;
		this.saveBudget = saveBudget;
		this.updateBudget = updateBudget;
		this.changeStatus = changeStatus;
		this.increaseAmount = increaseAmount;
		this.decreaseAmount = decreaseAmount;
        this.createPDF = createPDF;
    }
	
	@GetMapping("list/{id}/{listBy}")
	public ResponseEntity<IKResponse<BudgetDTO>> listBudgets(@PathVariable Long id, @PathVariable ListBudgetsByEnum listBy,
															 @RequestParam(name = "status") BudgetStatusEnum status) {
		try {
			List<BudgetDTO> budgetDTOList = converter.parseBudgetDTOList(listBudgets.execute(id, listBy, status));

			return ResponseEntity.ok(IKResponse.<BudgetDTO>build().body(budgetDTOList));

		} catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<BudgetDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<BudgetDTO>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetConstant.LIST_ERROR_MESSAGE))
			);
		}
	}

	@GetMapping("list/{workshopId}/{idDoc}/{status}")
	public ResponseEntity<IKResponse<BudgetDTO>> listBudgetsByCustomer(@PathVariable Long workshopId, @PathVariable String idDoc, @PathVariable BudgetStatusEnum status) {
		try {
			List<BudgetDTO> budgetDTOList = converter.parseBudgetDTOList(listBudgets.execute(workshopId, idDoc, status));

			return ResponseEntity.ok(IKResponse.<BudgetDTO>build().body(budgetDTOList));

		} catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<BudgetDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<BudgetDTO>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetConstant.LIST_ERROR_MESSAGE))
			);
		}
	}
	
	@GetMapping("get/{budgetId}")
	public ResponseEntity<IKResponse<BudgetDTO>> getBudget(@PathVariable Long budgetId) {
		try {
			Map<Long, Map<Long, Budget>> budgetAndVehicleMap = getBudget.execute(budgetId);

			for (Long vehicleId : budgetAndVehicleMap.keySet()) {
				Map<Long, Budget> budgetMap = budgetAndVehicleMap.get(vehicleId);
				return ResponseEntity.ok(IKResponse.<BudgetDTO>build().body(converter.parseBudgetDTO(budgetMap, vehicleId, false)));
			}

			throw new IKException(new IKMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.WARNING.getCode(), environment.getProperty(BudgetConstant.GET_ERROR_MESSAGE)));

		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<BudgetDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch (EntityNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					IKResponse.<BudgetDTO>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.WARNING, environment.getProperty(BudgetConstant.GET_NOT_FOUND_MESSAGE)));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<BudgetDTO>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetConstant.GET_ERROR_MESSAGE)));
		}
	}
	
	@PostMapping()
	@Transactional
	public ResponseEntity<IKResponse<BudgetDTO>> saveBudget(@RequestBody BudgetRequest budget, UriComponentsBuilder uriBuilder) {
		String budgetJSON = IKLoggerUtil.parseJSON(budget);
		String loggerID = IKLoggerUtil.getLoggerID();
		try {
			Map<Long, Budget> budgetMap = saveBudget.execute(converter.parseBudget(budget), budget.getVehicleId());
			
			ResponseEntity<IKResponse<BudgetDTO>> responseEntity = null;
			
			for(Long budgetId : budgetMap.keySet()) {
				URI uri = uriBuilder.path("budgets/{budgetId}").buildAndExpand(budgetId).toUri();
				
				responseEntity = ResponseEntity.created(uri).body(IKResponse.<BudgetDTO>build().body(
						converter.parseBudgetDTO(budgetMap, budget.getVehicleId(), true))
						.addMessage(BudgetBusinessConstant.SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(BudgetConstant.SAVE_SUCCESS_MESSAGE)));
				break;
			}
			
			return responseEntity;
			
		}catch(IKException ike) {
			LOGGER.error(loggerID + " - " + budgetJSON);
			LOGGER.error(loggerID + " - " + ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<BudgetDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(loggerID + " - " + budgetJSON);
			LOGGER.error(loggerID + " - " + e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<BudgetDTO>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetConstant.SAVE_ERROR_MESSAGE)));
		}
	}
	

	@PutMapping
	@Transactional
	public ResponseEntity<IKResponse<BudgetDTO>> updateBudget(@RequestBody BudgetRequest budget) {
		String budgetJSON = IKLoggerUtil.parseJSON(budget);
		String loggerID = IKLoggerUtil.getLoggerID();
		try {
			Map<Long, Budget> budgetMap = updateBudget.execute(converter.parseBudget(budget), budget.getBudgetId());
			
			return ResponseEntity.ok(IKResponse.<BudgetDTO>build().body(converter.parseBudgetDTO(budgetMap, null, false))
					.addMessage(BudgetBusinessConstant.SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(BudgetConstant.UPDATE_SUCCESS_MESSAGE)));
		}catch(IKException ike) {
			LOGGER.error(loggerID + " - " + budgetJSON);
			LOGGER.error(loggerID + " - " + ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<BudgetDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(loggerID + " - " + budgetJSON);
			LOGGER.error(loggerID + " - " + e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<BudgetDTO>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetConstant.UPDATE_ERROR_MESSAGE)));
		}
	}
	

	@PutMapping("{budgetId}/{value}")
	@Transactional
	public ResponseEntity<IKResponse<String>> increaseAmount(@PathVariable Long budgetId, @PathVariable BigDecimal value) {
		try {
			increaseAmount.execute(budgetId, value);
			
			return ResponseEntity.ok(IKResponse.<String>build()
					.addMessage(BudgetBusinessConstant.SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(BudgetConstant.OPERATION_SUCCESS_MESSAGE)));
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<String>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<String>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetConstant.OPERATION_ERROR_MESSAGE)));
		}
	}
	

	@PutMapping("changeStatus/{budgetId}/{budgetStatus}")
	@Transactional
	public ResponseEntity<IKResponse<String>> changeStatus(@PathVariable Long budgetId, @PathVariable int budgetStatus) {
		try {
			changeStatus.execute(budgetId, BudgetStatusEnum.findByIndex(budgetStatus));
			return ResponseEntity.ok(IKResponse.<String>build().addMessage(BudgetBusinessConstant.SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(BudgetConstant.OPERATION_SUCCESS_MESSAGE)));
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<String>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<String>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetConstant.OPERATION_ERROR_MESSAGE)));
		}
	}
	

	@PutMapping("decreaseAmount/{budgetId}/{value}")
	@Transactional
	public ResponseEntity<IKResponse<String>> decreaseAmount(@PathVariable Long budgetId, 
			@PathVariable BigDecimal value) {
		try {
			decreaseAmount.execute(budgetId, value);
			return ResponseEntity.ok(IKResponse.<String>build().addMessage(BudgetBusinessConstant.SUCCESS_CODE,
					IKMessageType.SUCCESS, environment.getProperty(BudgetConstant.OPERATION_SUCCESS_MESSAGE)));
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<String>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<String>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetConstant.OPERATION_ERROR_MESSAGE)));
		}
	}

    @GetMapping("budget/pdf/{budgetId}")
    public ResponseEntity<byte[]> createBudgetPDF(@PathVariable Long budgetId, HttpServletResponse response) {
        try {

            Map<Long, Map<Long, Budget>> budget = getBudget.execute(budgetId);

            byte[] file = createPDF.execute(budget);

			String fileName = createPDF.getFormattedPdfName();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", fileName);

			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(file);
        } catch (EntityNotFoundException e) {
			LOGGER.info("budgetId: " + budgetId);
            LOGGER.error(environment.getProperty(BudgetConstant.GET_NOT_FOUND_MESSAGE));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
