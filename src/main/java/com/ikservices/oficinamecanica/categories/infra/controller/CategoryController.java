package com.ikservices.oficinamecanica.categories.infra.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.ikservices.oficinamecanica.categories.application.usecases.GetCategory;
import com.ikservices.oficinamecanica.categories.application.usecases.GetNextCategoryId;
import com.ikservices.oficinamecanica.categories.application.usecases.ListCategory;
import com.ikservices.oficinamecanica.categories.application.usecases.SaveCategory;
import com.ikservices.oficinamecanica.categories.application.usecases.UpdateCategory;
import com.ikservices.oficinamecanica.categories.domain.Category;
import com.ikservices.oficinamecanica.categories.domain.CategoryId;
import com.ikservices.oficinamecanica.categories.infra.CategoryConverter;
import com.ikservices.oficinamecanica.categories.infra.constants.CategoryConstant;
import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.suppliers.infra.constants.SupplierConstants;
import com.ikservices.oficinamecanica.suppliers.infra.controller.SupplierDTO;

import lombok.Getter;

@RestController
@RequestMapping("categories")
public class CategoryController {
	private static final Logger LOGGER = IKLoggerUtil.getLogger(CategoryController.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	@Lazy
	private CategoryConverter converter;
	
	private final ListCategory listCategory;
	private final GetCategory getCategory;
	private final SaveCategory saveCategory;
	private final GetNextCategoryId getNextCategoryId;
	private final UpdateCategory updateCategory;
	
	public CategoryController(ListCategory listCategory, GetCategory getCategory,
			SaveCategory saveCategory, GetNextCategoryId getNextCategoryId,
			UpdateCategory updateCategory) {
		this.listCategory = listCategory;
		this.getCategory = getCategory;
		this.saveCategory = saveCategory;
		this.getNextCategoryId = getNextCategoryId;
		this.updateCategory = updateCategory;
	}	
	
	@GetMapping("list/{workshopId}")
	public ResponseEntity<IKResponse<CategoryDTO>> listCategory(@PathVariable Long workshopId) {
		try {
			List<CategoryDTO> categoryDTOList = converter.parseDomainToResponseList(listCategory.execute(workshopId));
			
			return ResponseEntity.ok(IKResponse.<CategoryDTO>build().body(categoryDTOList));			
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
					body(IKResponse.<CategoryDTO>build().addMessage(ike.getIkMessage().getCode(),
							IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
					body(IKResponse.<CategoryDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, 
							IKMessageType.ERROR, environment.getProperty(CategoryConstant.LIST_ERROR_MESSAGE)));
		}
	}
	
	@GetMapping("get/{categoryId}/{workshopId}")
	public ResponseEntity<IKResponse<CategoryDTO>> getCategory(@PathVariable Integer categoryId, 
			@PathVariable Long workshopId) {
		try {
			CategoryId catId = new CategoryId(categoryId, workshopId);
			CategoryDTO categoryDTO = converter.parseDomainToResponse(getCategory.execute(catId));
			
			return ResponseEntity.ok(IKResponse.<CategoryDTO>build().body(categoryDTO));			
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
					body(IKResponse.<CategoryDTO>build().addMessage(ike.getIkMessage().getCode(),
							IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
					body(IKResponse.<CategoryDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, 
							IKMessageType.ERROR, environment.getProperty(CategoryConstant.GET_ERROR_MESSAGE)));
		}
	}

	@PostMapping()
	public ResponseEntity<IKResponse<CategoryDTO>> saveCategory(@RequestBody CategoryDTO categoryDTO, 
			UriComponentsBuilder uriBuilder) {
		try {			
			Category category = null;
			
			Integer nextId = getNextCategoryId.execute(categoryDTO.getWorkshopId());
			categoryDTO.setCategoryId(nextId);
			
			category = saveCategory.execute(converter.parseRequestToDomain(categoryDTO));
			URI uri = uriBuilder.path("categories/{categoryId}/{workshopId}").
					buildAndExpand(category.getId().getCategoryId(), category.getId().getWorkshopId()).
					toUri();
			
			return ResponseEntity.created(uri).body(IKResponse.<CategoryDTO>build().
					body(converter.parseDomainToResponse(category)).addMessage(Constants.DEFAULT_SUCCESS_CODE,
							IKMessageType.SUCCESS, environment.getProperty(CategoryConstant.SAVE_SUCCESS_MESSAGE)));
		}catch(IKException ike) {
            LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(IKResponse.<CategoryDTO>build()
					.addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		} catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<CategoryDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, 
							environment.getProperty(CategoryConstant.SAVE_SUCCESS_MESSAGE)));
        }
	}
	
	@Transactional
	@PutMapping
	public ResponseEntity<IKResponse<CategoryDTO>> updateCategory(@RequestBody CategoryDTO categoryDTO) {
		try {
			Category category = updateCategory.execute(converter.parseRequestToDomain(categoryDTO));
			return ResponseEntity.ok(IKResponse.<CategoryDTO>build().body(converter.parseDomainToResponse(category)).
					addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS,
							environment.getProperty(CategoryConstant.UPDATE_SUCCESS_MESSAGE)));			
		}catch (IKException ike) {
            LOGGER.error(ike.getMessage(), ike);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    IKResponse.<CategoryDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    IKResponse.<CategoryDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(CategoryConstant.UPDATE_ERROR_MESSAGE)));
        }
	}
}
