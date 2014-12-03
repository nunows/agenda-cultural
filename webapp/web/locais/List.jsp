<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Locais</title>
            <link rel="stylesheet" type="text/css" href="/webapp/faces/jsfcrud.css" />
        </head>
        <body>
        <h:panelGroup id="messagePanel" layout="block">
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
        </h:panelGroup>
        <h1>Locais</h1>
        <h:form styleClass="jsfcrud_list_form">
            <h:outputText escape="false" value="Não existem locais<br />" rendered="#{locais.pagingInfo.itemCount == 0}" />
            <h:panelGroup rendered="#{locais.pagingInfo.itemCount > 0}">
                <h:outputText value="Item #{locais.pagingInfo.firstItem + 1}..#{locais.pagingInfo.lastItem} of #{locais.pagingInfo.itemCount}"/>&nbsp;
                <h:commandLink action="#{locais.prev}" value="Previous #{locais.pagingInfo.batchSize}" rendered="#{locais.pagingInfo.firstItem >= locais.pagingInfo.batchSize}"/>&nbsp;
                <h:commandLink action="#{locais.next}" value="Next #{locais.pagingInfo.batchSize}" rendered="#{locais.pagingInfo.lastItem + locais.pagingInfo.batchSize <= locais.pagingInfo.itemCount}"/>&nbsp;
                <h:commandLink action="#{locais.next}" value="Remaining #{locais.pagingInfo.itemCount - locais.pagingInfo.lastItem}"
                               rendered="#{locais.pagingInfo.lastItem < locais.pagingInfo.itemCount && locais.pagingInfo.lastItem + locais.pagingInfo.batchSize > locais.pagingInfo.itemCount}"/>
                <h:dataTable value="#{locais.locaisItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
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
                            <h:outputText value="Morada"/>
                        </f:facet>
                        <h:outputText value="#{item.morada}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Latitude"/>
                        </f:facet>
                        <h:outputText value="#{item.latitude}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Longitude"/>
                        </f:facet>
                        <h:outputText value="#{item.longitude}"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText escape="false" value="&nbsp;"/>
                        </f:facet>
                        <h:commandLink value="Ver" action="#{locais.detailSetup}">
                            <f:param name="jsfcrud.currentLocais" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][locais.converter].jsfcrud_invoke}"/>
                        </h:commandLink>
                        <h:outputText value=" "/>
                        <h:commandLink value="Alterar" action="#{locais.editSetup}">
                            <f:param name="jsfcrud.currentLocais" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][locais.converter].jsfcrud_invoke}"/>
                        </h:commandLink>
                        <h:outputText value=" "/>
                        <h:commandLink value="Apagar" action="#{locais.destroy}">
                            <f:param name="jsfcrud.currentLocais" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][locais.converter].jsfcrud_invoke}"/>
                        </h:commandLink>
                    </h:column>

                </h:dataTable>
            </h:panelGroup>
            <br />
            <h:commandLink action="#{locais.createSetup}" value="Criar"/>
            <br />
            <br />
            <h:commandLink value="Index" action="welcome" immediate="true" />


        </h:form>
        </body>
    </html>
</f:view>
