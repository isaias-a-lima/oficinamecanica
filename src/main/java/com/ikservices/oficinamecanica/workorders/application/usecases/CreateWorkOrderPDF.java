package com.ikservices.oficinamecanica.workorders.application.usecases;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.items.parts.domain.BudgetItemPart;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;
import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.pdf.PDFTemplateBuilder;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.items.parts.domain.WorkOrderPartItem;
import com.ikservices.oficinamecanica.workorders.items.services.domain.WorkOrderServiceItem;
import org.slf4j.Logger;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

public class CreateWorkOrderPDF extends PDFTemplateBuilder {

    private static final Logger LOGGER = IKLoggerUtil.getLogger(CreateWorkOrderPDF.class);

    private final String logo;
    private final String title;

    public CreateWorkOrderPDF(String pdfName, String logo, String title) {
        super(pdfName);
        this.logo = logo;
        this.title = title;
    }

    public byte[] execute(WorkOrder workOrder) {

        String loggerID = IKLoggerUtil.getLoggerID();
        String json = "";
        LOGGER.info(loggerID + " - " + this.getClass().getName() + ".execute");

        if (Objects.nonNull(workOrder)) {
            json = IKLoggerUtil.parseJSON(workOrder);
            LOGGER.info(loggerID + " - " + json);

            Budget budget = null;

            for (Long vehicleId : workOrder.getBudget().keySet()) {
                Map<Long, Budget> map2 = workOrder.getBudget().get(vehicleId);
                for (Long budgetId : map2.keySet()) {
                    budget = map2.get(budgetId);
                }
            }

            if (Objects.isNull(budget)) {
                throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR.getCode(), PDF_GENERATION_ERROR));
            }

            String workOrderNumber = NumberUtil.parseStringNumberWithLeftZeros(workOrder.getId().getWorkOrderId(), 4);

            try {
                this.openDocument(workOrderNumber);

                this.addHeaderImage(this.logo);

                this.addTitleH3(this.title + " - " + workOrderNumber, "left");


                //WorkOrder info table created
                float[] workOrderColumnWidths = {1.5f, 8.5f};
                String[][] workOrderContents = {
                        {"Emissão:", "1", workOrder.getOpeningDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), "0"}
                };
                this.addTableWithNoHeaderOrFooter(2, 100, workOrderColumnWidths, workOrderContents);


                //Customer info table created
                this.addTitleH4("Dados do cliente");
                String[][] customerContents = {
                        {"Nome:", "1", budget.getVehicle().getCustomer().getName(), "0", "CPF/CNPJ:", "1", budget.getVehicle().getCustomer().getId().getDocId().getFullDocument(), "0"},
                        {"Celular:", "1", budget.getVehicle().getCustomer().getMobilePhone().getFullPhone(), "0", "Telefone:", "1", budget.getVehicle().getCustomer().getLandline().getFullPhone(), "0"}
                };
                float[] customerColumnWidths = {1.5f, 3.5f, 1.5f, 3.5f};
                this.addTableWithNoHeaderOrFooter(4, 100, customerColumnWidths, customerContents);


                //Vehicle table created
                this.addTitleH4("Dados do veículo");
                float[] vehicleColumnWiths = {1.5f, 3.5f, 1.9f, 3.1f};
                String[][] vehicleContents = {
                        {"Descrição:", "1", budget.getVehicle().getBrand() + " | " + budget.getVehicle().getModel() + " | " + budget.getVehicle().getColor() , "0", "Ano/Modelo:", "1", budget.getVehicle().getManufacturing(), "0"},
                        {"Placa:", "1", budget.getVehicle().getPlate(), "0", "Km:", "1", budget.getKm().toString(), "0"}
                };
                this.addTableWithNoHeaderOrFooter(4, 100, vehicleColumnWiths, vehicleContents);


                //Services table created
                String[][] serviceAndPartHeaderColumns = {
                        {"#", LEFT},
                        {"Descrição", LEFT},
                        {"Qtd", LEFT},
                        {"Valor", LEFT},
                        {"Desconto", LEFT},
                        {"Total", LEFT}
                };
                float[] serviceColumnWidths = {0.6f, 4.1f, 0.7f, 1.6f, 1.4f, 1.6f};
                String[][] serviceItems = new String[workOrder.getServiceItems().size()][12];
                String[][] serviceAndPartFooterColumns = {{" ", LEFT}, {" ", LEFT}, {" ", LEFT}, {" ", LEFT}, {"TOTAL", RIGHT}, {workOrder.sumServiceItems(), LEFT}};
                int x = 0;
                for (WorkOrderServiceItem serviceItem : workOrder.getServiceItems()) {

                    serviceItems[x][0] = serviceItem.getItemId().getItemId().toString();
                    serviceItems[x][1] = "0";
                    serviceItems[x][2] = serviceItem.getService().getDescription();
                    serviceItems[x][3] = "0";
                    serviceItems[x][4] = serviceItem.getQuantity().toString();
                    serviceItems[x][5] = "0";
                    serviceItems[x][6] = NumberUtil.parseStringMoney(serviceItem.getService().getCost());
                    serviceItems[x][7] = "0";
                    serviceItems[x][8] = NumberUtil.parseStringPercent(serviceItem.getDiscount());
                    serviceItems[x][9] = "0";
                    serviceItems[x][10] = NumberUtil.parseStringMoney(serviceItem.getTotal());
                    serviceItems[x][11] = "0";

                    x++;
                }

                if (!workOrder.getServiceItems().isEmpty()) {
                    this.addTitleH4("Serviços");
                    this.addFullTable(serviceAndPartHeaderColumns, serviceColumnWidths, serviceAndPartFooterColumns, serviceItems);
                }

                //Parts table created
                this.addTitleH4("Peças/Produtos");
                String[][] partItems = new String[workOrder.getPartItems().size()][12];
                String[][] partFooterColumns = {{" ", LEFT}, {" ", LEFT}, {" ", LEFT}, {" ", LEFT}, {"TOTAL", RIGHT}, {workOrder.sumPartItems(), LEFT}};
                int y = 0;
                for (WorkOrderPartItem partItem : workOrder.getPartItems()) {
                    partItems[y][0] = partItem.getId().getItemId().toString();
                    partItems[y][1] = "0";
                    partItems[y][2] = partItem.getPart().getDescription();
                    partItems[y][3] = "0";
                    partItems[y][4] = partItem.getQuantity().toString();
                    partItems[y][5] = "0";
                    partItems[y][6] = NumberUtil.parseStringMoney(partItem.getItemValue().add(partItem.getServiceCost()));
                    partItems[y][7] = "0";
                    partItems[y][8] = NumberUtil.parseStringPercent(partItem.getDiscount());
                    partItems[y][9] = "0";
                    partItems[y][10] = NumberUtil.parseStringMoney(partItem.getTotal());
                    partItems[y][11] = "0";
                    y++;
                }

                this.addFullTable(serviceAndPartHeaderColumns, serviceColumnWidths, partFooterColumns, partItems);

                //Amount table created
                float[] columnWidths = {8.4f, 1.6f};
                String [][] amountTableContents = {{"TOTAL GERAL: ",RIGHT}, {NumberUtil.parseStringMoney(workOrder.getAmount()), LEFT}};
                this.addTableFooter(2, 100, columnWidths, amountTableContents);

            } catch (Exception e) {
                LOGGER.error(loggerID + " - " + e.getMessage(), e);
                throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR.getCode(), PDF_GENERATION_ERROR));
            } finally {
                try {
                    this.closeDocument();
                } catch (IOException e) {
                    LOGGER.error(DOCUMENT_CLOSE_ERROR);
                }
            }
        }

        return this.file;
    }
}
