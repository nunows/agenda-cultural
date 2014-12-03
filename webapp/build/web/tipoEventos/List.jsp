<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Tipo de Eventos</title>
            <link rel="stylesheet" type="text/css" href="/webapp/faces/jsfcrud.css" />
        </head>
        <body>
        <h:panelGroup id="messagePanel" layout="block">
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
        </h:panelGroup>
        <h1>Tipo de Eventos</h1>
        <h:form styleClass="jsfcrud_list_form">
            <h:outputText escape="false" value="Não existem tipo de eventos.<br />" rendered="#{tipoEventos.pagingInfo.itemCount == 0}" />
            <h:panelGroup rendered="#{tipoEventos.pagingInfo.itemCount > 0}">
                <h:outputText value="Item #{tipoEventos.pagingInfo.firstItem + 1}..#{tipoEventos.pagingInfo.lastItem} of #{tipoEventos.pagingInfo.itemCount}"/>&nbsp;
                <h:commandLink action="#{tipoEventos.prev}" value="Previous #{tipoEventos.pagingInfo.batchSize}" rendered="#{tipoEventos.pagingInfo.firstItem >= tipoEventos.pagingInfo.batchSize}"/>&nbsp;
                <h:commandLink action="#{tipoEventos.next}" value="Next #{tipoEventos.pagingInfo.batchSize}" rendered="#{tipoEventos.pagingInfo.lastItem + tipoEventos.pagingInfo.batchSize <= tipoEventos.pagingInfo.itemCount}"/>&nbsp;
                <h:commandLink action="#{tipoEventos.next}" value="Remaining #{tipoEventos.pagingInfo.itemCount - tipoEventos.pagingInfo.lastItem}"
                               rendered="#{tipoEventos.pagingInfo.lastItem < tipoEventos.pagingInfo.itemCount && tipoEventos.pagingInfo.lastItem + tipoEventos.pagingInfo.batchSize > tipoEventos.pagingInfo.itemCount}"/>
                <h:dataTable value="#{tipoEventos.tipoEventosItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
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
                            <h:outputText escape="false" value="&nbsp;"/>
                        </f:facet>
                        <h:commandLink value="Ver" action="#{tipoEventos.detailSetup}">
                            <f:param name="jsfcrud.currentTipoEventos" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][tipoEventos.converter].jsfcrud_invoke}"/>
                        </h:commandLink>
                        <h:outputText value=" "/>
                        <h:commandLink value="Alterar" action="#{tipoEventos.editSetup}">
                            <f:param name="jsfcrud.currentTipoEventos" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][tipoEventos.converter].jsfcrud_invoke}"/>
                        </h:commandLink>
                        <h:outputText value=" "/>
                        <h:commandLink value="Apagar" action="#{tipoEventos.destroy}">
                            <f:param name="jsfcrud.currentTipoEventos" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][tipoEventos.converter].jsfcrud_invoke}"/>
                        </h:commandLink>
                    </h:column>

                </h:dataTable>
            </h:panelGroup>
            <br />
            <h:commandLink action="#{tipoEventos.createSetup}" value="Criar"/>
            <br />
            <br />
            <h:commandLink value="Index" action="welcome" immediate="true" />


        </h:form>
        </body>
    </html>
</f:view>
