<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Tipo de Evento - Detalhe</title>
            <link rel="stylesheet" type="text/css" href="/webapp/faces/jsfcrud.css" />
        </head>
        <body>
        <h:panelGroup id="messagePanel" layout="block">
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
        </h:panelGroup>
        <h1>Tipo de Evento - Detalhe</h1>
        <h:form>
            <h:panelGrid columns="2">
                <h:outputText value="Id:"/>
                <h:outputText value="#{tipoEventos.tipoEventos.id}" title="Id" />
                <h:outputText value="Nome:"/>
                <h:outputText value="#{tipoEventos.tipoEventos.nome}" title="Nome" />
     
            </h:panelGrid>
            <br />
            <h:commandLink action="#{tipoEventos.listSetup}" value="Voltar..."/>
           
        </h:form>
        </body>
    </html>
</f:view>
