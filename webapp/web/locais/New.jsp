<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Local - Criar</title>
            <link rel="stylesheet" type="text/css" href="/webapp/faces/jsfcrud.css" />
        </head>
        <body>

    


        <h:panelGroup id="messagePanel" layout="block">
            <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
        </h:panelGroup>
        <h1>Local - Criar</h1>
        <h:form>
            <h:inputHidden id="validateCreateField" validator="#{locais.validateCreate}" value="value"/>
            <h:panelGrid columns="2">
                <h:outputText value="Nome:"/>
                <h:inputText id="nome" value="#{locais.locais.nome}" title="Nome" required="true" requiredMessage="The nome field is required." />
                <h:outputText value="Morada:"/>
                <h:inputText id="morada" value="#{locais.locais.morada}" title="Morada" required="true" requiredMessage="The morada field is required." />
                
                <h:column></h:column>
                <h:column><input type="button" value="Pesquisar no Mapa" onclick="findAddress()"></h:column>

                <h:outputText value="Mapa:"/>

                <h:column>

                    <div id="map_canvas" style="width: 400px; height: 200px;"></div>

                </h:column>

                <h:column></h:column>
                <h:column><span id="lat"></span> | <span id="lon"></span>

                    &nbsp;<a href="">Usar coordenadas</a>

                </h:column>


                <h:outputText value="Latitude:"/>
                <h:inputText id="latitude" value="#{locais.locais.latitude}" title="Latitude" required="true" requiredMessage="The latitude field is required." />
                <h:outputText value="Longitude:"/>
                <h:inputText id="longitude" value="#{locais.locais.longitude}" title="Longitude" required="true" requiredMessage="The longitude field is required." />
                
            </h:panelGrid>
            <br />
            <h:commandLink action="#{locais.create}" value="Criar"/>          
            <br />
            <br />
            <h:commandLink value="Index" action="welcome" immediate="true" />

        </h:form>

            

  <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false&language=pt"></script>
  <script type="text/javascript">

  var geocoder;
  var map;
  function initialize() {
    geocoder = new google.maps.Geocoder();
    var latlng = new google.maps.LatLng(38.706929,-9.135625);
    var myOptions = {
      zoom: 15,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
  }

  function findAddress() {
    var address = document.getElementById("j_id_jsp_645082186_3:morada").value;
    geocoder.geocode( { 'address': address}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
        map.setCenter(results[0].geometry.location);
        var marker = new google.maps.Marker({
            map: map,
            position: results[0].geometry.location
        });

        var location = map.getCenter();

        document.getElementById("lat").innerHTML = location.lat();
        document.getElementById("lon").innerHTML = location.lng();
       
      } else {
        //alert("Erro: " + status);
      }
    });
  }

   google.maps.event.addDomListener(window, 'load', initialize);
    </script>
   
        </body>
    </html>
</f:view>
