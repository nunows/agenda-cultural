<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Evento - Editar</title>
            <link rel="stylesheet" type="text/css" href="/webapp/faces/jsfcrud.css" />
        </head>
        <body>
        <h:panelGroup id="messagePanel" layout="block">
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
        </h:panelGroup>
        <h1>Evento - Editar</h1>
        <h:form>
            <h:panelGrid columns="2">
                <h:outputText value="Id:"/>
                <h:outputText value="#{eventos.eventos.id}" title="Id" />
                <h:outputText value="Nome:"/>
                <h:inputText id="nome" value="#{eventos.eventos.nome}" title="Nome" required="true" requiredMessage="Campo nome é obrigatório." />
                <h:outputText value="Data/Hora (MM/dd/yyyy HH:mm:ss):"/>
                <h:inputText id="datahora" value="#{eventos.eventos.datahora}" title="Datahora" required="true" requiredMessage="Campo data hora é obrigatório." >
                    <f:convertDateTime pattern="MM/dd/yyyy HH:mm:ss" />
                </h:inputText>
                <h:outputText value="Imagem Url:"/>
                <h:inputText id="imagemUrl" value="#{eventos.eventos.imagemUrl}" title="ImagemUrl" />
                <h:outputText value="Tipo de Evento:"/>
                <h:selectOneMenu id="tipoEventos" value="#{eventos.eventos.tipoEventos}" title="TipoEventos" required="true" requiredMessage="Campo Tipo Evento é obrigatório." >
                    <f:selectItems value="#{tipoEventos.tipoEventosItemsAvailableSelectOne}"/>
                </h:selectOneMenu>
                <h:outputText value="Local:"/>
                <h:selectOneMenu id="locais" value="#{eventos.eventos.locais}" title="Locais" required="true" requiredMessage="Campo local é obrigatório." >
                    <f:selectItems value="#{locais.locaisItemsAvailableSelectOne}"/>
                </h:selectOneMenu>

            </h:panelGrid>
            <br />
            <h:commandLink action="#{eventos.edit}" value="Gravar">
                <f:param name="jsfcrud.currentEventos" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][eventos.eventos][eventos.converter].jsfcrud_invoke}"/>
            </h:commandLink>
            <br />
            <br />
            <h:commandLink action="#{eventos.listSetup}" value="Voltar..." immediate="true"/>
           

        </h:form>
        </body>
    </html>
</f:view>
