<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Evento - Detalhe</title>
            <link rel="stylesheet" type="text/css" href="/webapp/faces/jsfcrud.css" />
        </head>
        <body>
        <h:panelGroup id="messagePanel" layout="block">
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
        </h:panelGroup>
        <h1>Eventos - Detalhe</h1>
        <h:form>
            <h:panelGrid columns="2">
                <h:outputText value="Id:"/>
                <h:outputText value="#{eventos.eventos.id}" title="Id" />
                <h:outputText value="Nome:"/>
                <h:outputText value="#{eventos.eventos.nome}" title="Nome" />
                <h:outputText value="Data/Hora:"/>
                <h:outputText value="#{eventos.eventos.datahora}" title="Datahora" >
                    <f:convertDateTime pattern="MM/dd/yyyy HH:mm:ss" />
                </h:outputText>
                <h:outputText value="Imagem Url:"/>
                <h:outputText value="#{eventos.eventos.imagemUrl}" title="ImagemUrl" />
                <h:outputText value="Tipo Evento:"/>
                <h:panelGroup>
                    <h:outputText value="#{eventos.eventos.tipoEventos.nome}"/>
                </h:panelGroup>
                <h:outputText value="Local:"/>
                <h:panelGroup>
                    <h:outputText value="#{eventos.eventos.locais.nome}"/>
                </h:panelGroup>
            </h:panelGrid>
            
            <br />
            <h:commandLink action="#{eventos.listSetup}" value="Voltar..."/>
            
        </h:form>
        </body>
    </html>
</f:view>
