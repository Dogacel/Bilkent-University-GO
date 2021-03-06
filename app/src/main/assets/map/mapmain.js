
//**************************************************************************************************
//popup
var container = document.getElementById('popup');
var content = document.getElementById('popup-content');
var closer = document.getElementById('popup-closer');

var overlay = new ol.Overlay({
    element: container,
    autoPan: true,
    autoPanAnimation: {
        duration: 250
    }
});


/**
 * Add a click handler to hide the popup.
 * @return {boolean} Don't follow the href.
 */
closer.onclick = function () {
    overlay.setPosition(undefined);
    closer.blur();
    return false;
};

//*****************************************************************************************************


// Githuba yüklerken ata.yurtsever kalsın
var geoURL = 'http://ata.yurtsever.ug.bilkent.edu.tr/map.geojson';

//var geoURL = 'https://raw.githubusercontent.com/Dogacel/teamazm-cs102/master/app/src/main/res/raw/map.geojson?token=AioIuoMy1dZBgyAWXXApORDeuNRqM9Xyks5a_tcDwA%3D%3D';


//request and take geoUrl json
var request = new XMLHttpRequest();
request.open('GET', geoURL);
request.responseType = 'json';
request.send();

/**
 * onLoad function of the XMLHTTPRequest
 */
request.onload = function () {
    var geo = request.response;
    addSelectIndexes(geo);
}


/**
 * adds select indexes from given array
 * @param {json} arr 
 */
function addSelectIndexes(arr) {
    html = '';
    html += "<option value=[\"32.748428\",\"39.86800\"] > Bilkent </option>";
    for (var i = 0; i < arr.features.length; i++) {
        html += "<option value=" + middlePointArray(arr.features[i].geometry.coordinates[0])
            + ">" + arr.features[i].properties["name"] + "</option>";
    }

    document.getElementById("Buildings").innerHTML = html;
}


/**
 * returns middle point of given coordinates
 * @param {String array} arrr 
 */
function middlePointArray(arrr) {
    //returns as string solved it a month ago but cant remember how it works maybe solve it in the future
    //look at the point where I define all building it solution is there
    var xMean = 0, yMean = 0;
    for (var i = 0; i < arrr.length; i++) {
        xMean = xMean + arrr[i][0];
        yMean = yMean + arrr[i][1];
    }
    xMean /= arrr.length;
    yMean /= arrr.length;

    return [xMean, yMean];
}


var fenFakHavuzCoor = [32.748428, 39.86800];
var fenFakHavuz = ol.proj.fromLonLat(fenFakHavuzCoor);

var view = new ol.View({//start view
    center: fenFakHavuz,
    zoom: 16
})

var raster = new ol.layer.Tile({//main map
    source: new ol.source.OSM()
});




var vector = new ol.layer.Vector({//geojson vectors
    id: 'vec',
    source: new ol.source.Vector({
        url: geoURL,
        format: new ol.format.GeoJSON()
    }),
    opacity: 0.3
});

//Select layer
var selectLayer = new ol.style.Style({
    stroke: new ol.style.Stroke({
        color: '#303144',//stroke color
        width: 2.5
    })
})

var selectInteraction = new ol.interaction.Select({//Select Interaction
    condition: ol.events.condition.singleClick,
    toggleCondition: ol.events.condition.never,
    layers: function (layer) {
        return layer.get('id') == 'vec';
    },
    style: selectLayer
})


//Location marker
var locFeature = new ol.Feature({
    geometry: new ol.geom.Point(fenFakHavuz)
});

locFeature.setStyle( new ol.style.Style({
    image: new ol.style.Icon(({
        src: './icon.png',
        scale: 0.07
    }))
}))

var locVector = new ol.layer.Vector({
    source: new ol.source.Vector({
        features: [locFeature]
    })
})




//create map from raster and vector layers
var map = new ol.Map({
    //interactions : ol.interaction.defaults().extend([select]),
    layers: [raster, vector, locVector],
    target: 'map',
    view: view,
    overlays: [overlay]
});


map.getInteractions().extend([selectInteraction]);



//------------------------------------------------Select
var selected;
var selectedId;


/**
 * map on click function highlights buildings
 */
map.on('pointermove', function (evt) {
    if (evt.dragging) {
        return;
    }
    var pixel = map.getEventPixel(evt.originalEvent);
    //displayFeatureInfo(pixel);
});


/**
 * map on click function gets the building info
 */
map.on('singleclick', function (evt) {
    var coordinate = evt.coordinate;
    displayFeatureInfo(evt.pixel, coordinate);
});

/**
 * displays feature info
 * @param {given pixe} pixel 
 * @param {coordinate array} coordinate 
 */
var displayFeatureInfo = function (pixel, coordinate) {
    var feature = map.forEachFeatureAtPixel(pixel, function (feature) {
        return feature;
    });


    if (feature) {
        //alert('name : ' + feature.get('name'));
        //var hdms = ol.coordinate.toStringHDMS(ol.proj.transform( coordinate, 'EPSG:3857', 'EPSG:4326'));
        if(feature.get('name') != undefined){
            
            selected = feature.get('name');

            selectedId = feature.get('id');
            var selectedInfo = feature.get('Information');
            /*
            content.innerHTML = '<p>' + feature.get('name') + '</p>'
            + '<button onclick="panelButton()"> More Information </button>' ;
            */
            show(selected, selectedInfo);
            overlay.setPosition(coordinate);
            
        }

    } else {

    }

}

//--------------------------------------------------

var imgHtml =  "<img width='30px' src='CloseWindowGr48.png' />";
/**
 * updates popup
 * @param {string} buildingName 
 * @param {string} buildingSubTitle 
 */
function show(buildingName, buildingSubTitle)
{
    document.getElementById('popup').style.display = "block";

    var subHtml="";
    if(buildingSubTitle!="")
    {
        subHtml="<br><span class='buildingSubTitle'>" + buildingSubTitle +  "</span>";
    }
    var html="<div class='buildingTitle'>"+ buildingName +  subHtml + "</div><div class='ol-popup-closer' onclick='hide()'>"+imgHtml+"</div>"+"<div  class='buildingButton' onclick='panelButton()'> More Information </div>";
    
    document.getElementById('popup-content').innerHTML =  html;
}

/**
 * popup buttons
 */
function panelButton(){
    console.log(selectedId + '==' + selected);
    //Android.showToast('' + selectedId);
    Android.goPanel('' + selectedId);
};

/**
 * hides the popup
 */
function hide()
{
    document.getElementById('popup').style.display = "none";
} 



//flyTo function
function flyTo(location, done, zoom) {
    var duration = 2000;
    var parts = 2;
    var called = false;
    function callback(complete) {
        --parts;
        if (called) {
            return;
        }
        if (parts === 0 || !complete) {
            called = true;
            done(complete);
        }
    }
    view.animate({
        center: location,
        duration: duration
    }, callback);
    view.animate({
        zoom: zoom - 1,
        duration: duration / 2
    }, {
            zoom: zoom,
            duration: duration / 2
        }, callback);
}
//-----
//Buildings dropdown interaction
function onChange() {
    var x = document.getElementById('Buildings').value;
    var y = x.split(',');
    var x1 = parseFloat(y[0]);
    var x2 = parseFloat(y[1]);

    console.log(y);
    console.log(x);
    var buildingView = ol.proj.fromLonLat([x1, x2]);
    console.log(buildingView);

    flyTo(buildingView, function () { }, 18);
    //locFeature.setGeometry(new ol.geom.Point(buildingView));
};

// to Bilkent button interaction


//document.getElementById('popupButton').addEventListener('click', function () {
//    alert('button');
//});

function updateFromAndroid(lon, lat) {
    var coor = [parseFloat(lon),parseFloat(lat)];
    var point = new ol.proj.fromLonLat(coor);
    //flyTo(point, function(){}, 16);

    //Android.showToast(lon + "==Hello==" + lat);
    locFeature.setGeometry( new ol.geom.Point(point));
}


