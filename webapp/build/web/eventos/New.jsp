<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Evento - Criar</title>
            <link rel="stylesheet" type="text/css" href="/webapp/faces/jsfcrud.css" />
        </head>
        <body>
        <h:panelGroup id="messagePanel" layout="block">
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
        </h:panelGroup>
        <h1>Evento - Criar</h1>
        <h:form>
            <h:inputHidden id="validateCreateField" validator="#{eventos.validateCreate}" value="value"/>
            <h:panelGrid columns="2">
                <h:outputText value="Nome:"/>
                <h:inputText id="nome" value="#{eventos.eventos.nome}" title="Nome" required="true" requiredMessage="Campo nome é obrigatório." />
                <h:outputText value="Data/Hora (MM/dd/yyyy HH:mm:ss):"/>
                <h:inputText id="datahora" value="#{eventos.eventos.datahora}" title="Data / Hora" required="true" requiredMessage="Campo data hora é obrigatório." >
                    <f:convertDateTime pattern="MM/dd/yyyy HH:mm:ss" />
                </h:inputText>
                <h:outputText value="Imagem Url:"/>
                <h:inputText id="imagemUrl" value="#{eventos.eventos.imagemUrl}" title="Imagem Url" />
                <h:outputText value="Tipo Evento:"/>
                <h:selectOneMenu id="tipoEventos" value="#{eventos.eventos.tipoEventos}" title="Tipo de Evento" required="true" requiredMessage="Campo Tipo Evento é obrigatório." >
                    <f:selectItems value="#{tipoEventos.tipoEventosItemsAvailableSelectOne}"/>
                </h:selectOneMenu>
                <h:outputText value="Local:"/>
                <h:selectOneMenu id="locais" value="#{eventos.eventos.locais}" title="Local" required="true" requiredMessage="Campo local é obrigatório." >
                    <f:selectItems value="#{locais.locaisItemsAvailableSelectOne}"/>
                </h:selectOneMenu>

            </h:panelGrid>
            <br />
            <h:commandLink action="#{eventos.create}" value="Criar"/>
            <br />
            <br />
            <h:commandLink action="#{eventos.listSetup}" value="Voltar..." immediate="true"/>
           

        </h:form>
        </body>
    </html>
</f:view>
