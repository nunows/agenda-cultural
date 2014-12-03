<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Eventos</title>
            <link rel="stylesheet" type="text/css" href="/webapp/faces/jsfcrud.css" />
        </head>
        <body>
        <h:panelGroup id="messagePanel" layout="block">
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
        </h:panelGroup>
        <h1>Eventos</h1>
        <h:form styleClass="jsfcrud_list_form">
            <h:outputText escape="false" value="Não existem eventos.<br />" rendered="#{eventos.pagingInfo.itemCount == 0}" />
            <h:panelGroup rendered="#{eventos.pagingInfo.itemCount > 0}">
                <h:outputText value="Item #{eventos.pagingInfo.firstItem + 1}..#{eventos.pagingInfo.lastItem} of #{eventos.pagingInfo.itemCount}"/>&nbsp;
                <h:commandLink action="#{eventos.prev}" value="Previous #{eventos.pagingInfo.batchSize}" rendered="#{eventos.pagingInfo.firstItem >= eventos.pagingInfo.batchSize}"/>&nbsp;
                <h:commandLink action="#{eventos.next}" value="Next #{eventos.pagingInfo.batchSize}" rendered="#{eventos.pagingInfo.lastItem + eventos.pagingInfo.batchSize <= eventos.pagingInfo.itemCount}"/>&nbsp;
                <h:commandLink action="#{eventos.next}" value="Remaining #{eventos.pagingInfo.itemCount - eventos.pagingInfo.lastItem}"
                               rendered="#{eventos.pagingInfo.lastItem < eventos.pagingInfo.itemCount && eventos.pagingInfo.lastItem + eventos.pagingInfo.batchSize > eventos.pagingInfo.itemCount}"/>
                <h:dataTable value="#{eventos.eventosItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Id"/>
                        </f:facet>
                        <h:outputText value="#{item.id}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Nome"/>
                        </f:facet>
                        <h:outputText value="#{item.nome}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Data Hora"/>
                        </f:facet>
                        <h:outputText value="#{item.datahora}">
                            <f:convertDateTime pattern="MM/dd/yyyy HH:mm:ss" />
                        </h:outputText>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Imagem Url"/>
                        </f:facet>
                        <h:outputText value="#{item.imagemUrl}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText escape="false" value="&nbsp;"/>
                        </f:facet>
                        <h:commandLink value="Ver" action="#{eventos.detailSetup}">
                            <f:param name="jsfcrud.currentEventos" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][eventos.converter].jsfcrud_invoke}"/>
                        </h:commandLink>
                        <h:outputText value=" "/>
                        <h:commandLink value="Alterar" action="#{eventos.editSetup}">
                            <f:param name="jsfcrud.currentEventos" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][eventos.converter].jsfcrud_invoke}"/>
                        </h:commandLink>
                        <h:outputText value=" "/>
                        <h:commandLink value="Apagar" action="#{eventos.destroy}">
                            <f:param name="jsfcrud.currentEventos" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][eventos.converter].jsfcrud_invoke}"/>
                        </h:commandLink>
                    </h:column>
                </h:dataTable>
            </h:panelGroup>
            <br />
            <h:commandLink action="#{eventos.createSetup}" value="Criar"/>
            <br />
            <br />
            <h:commandLink value="Index" action="welcome" immediate="true" />

        </h:form>
        </body>
    </html>
</f:view>
