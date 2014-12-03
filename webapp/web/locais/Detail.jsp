<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Local - Detalhe</title>
            <link rel="stylesheet" type="text/css" href="/webapp/faces/jsfcrud.css" />
        </head>
        <body>
        <h:panelGroup id="messagePanel" layout="block">
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
        </h:panelGroup>
        <h1>Local - Detalhe</h1>
        <h:form>
            <h:panelGrid columns="2">
                <h:outputText value="Id:"/>
                <h:outputText value="#{locais.locais.id}" title="Id" />
                <h:outputText value="Nome:"/>
                <h:outputText value="#{locais.locais.nome}" title="Nome" />
                <h:outputText value="Morada:"/>
                <h:outputText value="#{locais.locais.morada}" title="Morada" />
                <h:outputText value="Latitude:"/>
                <h:outputText value="#{locais.locais.latitude}" title="Latitude" id="lat" />
                <h:outputText value="Longitude:"/>
                <h:outputText value="#{locais.locais.longitude}" title="Longitude" id="lon" />

                

            </h:panelGrid>
            
  <div id="map_canvas" style="width: 400px; height: 200px;"></div>
  <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false&language=pt"></script>
  <script type="text/javascript">


  var map;
  function initialize() {

   var lat = document.getElementById("j_id_jsp_1697324853_3:lat").innerHTML;
   var lon = document.getElementById("j_id_jsp_1697324853_3:lon").innerHTML;


    var latlng = new google.maps.LatLng(lat,lon);
    var myOptions = {
      zoom: 17,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }

    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

    var marker = new google.maps.Marker({
      position: latlng,
      map: map
    });

  }

   google.maps.event.addDomListener(window, 'load', initialize);
  </script>


            <br>
            <h:commandLink action="#{locais.listSetup}" value="Voltar..."/>
            
        </h:form>
        </body>
    </html>
</f:view>
