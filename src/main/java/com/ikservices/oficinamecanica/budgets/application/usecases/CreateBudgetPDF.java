package com.ikservices.oficinamecanica.budgets.application.usecases;

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
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

public class CreateBudgetPDF extends PDFTemplateBuilder {

    private static final Logger LOGGER = IKLoggerUtil.getLogger(CreateBudgetPDF.class);

    private final String logo;
    private final String title;


    public CreateBudgetPDF(String pdfName, String logo, String title) {
        super(pdfName);
        this.logo = logo;
        this.title = title;
    }

    public byte[] execute(Map<Long, Map<Long, Budget>> budgetMap) {

        String loggerID = IKLoggerUtil.getLoggerID();
        String json = "";
        LOGGER.info(loggerID + " - " + this.getClass().getName() + ".execute");

        Budget budget = null;

        String budgetNumber = null;

        for (Long vehicleId : budgetMap.keySet()) {
            Map<Long, Budget> map2 = budgetMap.get(vehicleId);
            for (Long budgetId : map2.keySet()) {
                budget = map2.get(budgetId);
                budgetNumber = NumberUtil.parseStringNumberWithLeftZeros(budgetId, 4);
            }
        }

        if (Objects.nonNull(budget)) {
            json = IKLoggerUtil.parseJSON(budget);
            LOGGER.info(loggerID + " - " + json);
            try {
                this.openDocument(budgetNumber);

                this.addHeaderImage(this.logo);

                this.addTitleH3(this.title + " - " + budgetNumber, "left");
                //this.addParagraph("Data: " + budget.getOpeningDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                //Budget info table created
                float[] budgetColumnWidths = {1.5f, 3.5f, 1.5f, 3.5f};
                String[][] budgetContents = {
                        {"Emissão:", "1", budget.getOpeningDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), "0",
                                "Validade:", "1", budget.getOpeningDate().plusDays(15).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), "0"}
                };
                this.addTableWithNoHeaderOrFooter(4, 100, budgetColumnWidths, budgetContents);

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
                this.addTitleH4("Serviços");
                String[][] serviceAndPartHeaderColumns = {
                        {"#", LEFT},
                        {"Descrição", LEFT},
                        {"Qtd", LEFT},
                        {"Valor", LEFT},
                        {"Desconto", LEFT},
                        {"Total", LEFT}
                };
                float[] serviceColumnWidths = {0.6f, 4.1f, 0.7f, 1.6f, 1.4f, 1.6f};
                String[][] serviceItems = new String[budget.getServiceItems().size()][12];
                String[][] serviceAndPartFooterColumns = {{" ", LEFT}, {" ", LEFT}, {" ", LEFT}, {" ", LEFT}, {"TOTAL", RIGHT}, {budget.sumServiceItems(), LEFT}};
                int x = 0;
                for (BudgetItemService serviceItem : budget.getServiceItems()) {

                    serviceItems[x][0] = serviceItem.getItemId().getId().toString();
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
                this.addFullTable(serviceAndPartHeaderColumns, serviceColumnWidths, serviceAndPartFooterColumns, serviceItems);

                //Parts table created
                this.addTitleH4("Peças/Produtos");
                String[][] partItems = new String[budget.getPartItems().size()][12];
                String[][] partFooterColumns = {{" ", LEFT}, {" ", LEFT}, {" ", LEFT}, {" ", LEFT}, {"TOTAL", RIGHT}, {budget.sumPartItems(), LEFT}};
                int y = 0;
                for (BudgetItemPart partItem : budget.getPartItems()) {
                    partItems[y][0] = partItem.getId().getItemId().toString();
                    partItems[y][1] = "0";
                    partItems[y][2] = partItem.getPart().getDescription();
                    partItems[y][3] = "0";
                    partItems[y][4] = partItem.getQuantity().toString();
                    partItems[y][5] = "0";
                    partItems[y][6] = NumberUtil.parseStringMoney(partItem.getPart().getCost());
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
                String [][] amountTableContents = {{"TOTAL GERAL: ",RIGHT}, {NumberUtil.parseStringMoney(budget.getAmount()), LEFT}};
                this.addTableFooter(2, 100, columnWidths, amountTableContents);

                this.closeDocument();

            } catch (Exception e) {
                LOGGER.error(loggerID + " - " + e.getMessage(), e);
                throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR.getCode(), "Erro na geração do PDF de Orçamento."));
            }
        }

        return this.file;
    }
}
