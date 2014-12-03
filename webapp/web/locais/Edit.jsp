<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Local - Alterar</title>
            <link rel="stylesheet" type="text/css" href="/webapp/faces/jsfcrud.css" />
        </head>
        <body>
        <h:panelGroup id="messagePanel" layout="block">
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
        </h:panelGroup>
        <h1>Local - Alterar</h1>
        <h:form>
            <h:panelGrid columns="2">
                <h:outputText value="Id:"/>
                <h:outputText value="#{locais.locais.id}" title="Id" />
                <h:outputText value="Nome:"/>
                <h:inputText id="nome" value="#{locais.locais.nome}" title="Nome" required="true" requiredMessage="The nome field is required." />
                <h:outputText value="Morada:"/>
                <h:inputText id="morada" value="#{locais.locais.morada}" title="Morada" required="true" requiredMessage="The morada field is required." />
                <h:outputText value="Latitude:"/>
                <h:inputText id="latitude" value="#{locais.locais.latitude}" title="Latitude" required="true" requiredMessage="The latitude field is required." />
                <h:outputText value="Longitude:"/>
                <h:inputText id="longitude" value="#{locais.locais.longitude}" title="Longitude" required="true" requiredMessage="The longitude field is required." />
              
            </h:panelGrid>
            <br />
            <h:commandLink action="#{locais.edit}" value="Gravar">
                <f:param name="jsfcrud.currentLocais" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][locais.locais][locais.converter].jsfcrud_invoke}"/>
            </h:commandLink>
            <br />
            <br />
            <h:commandLink value="Index" action="welcome" immediate="true" />

        </h:form>
        </body>
    </html>
</f:view>
