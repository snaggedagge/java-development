<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="container">
    <img src="/images/logo.png" style="width:inherit;max-width: 100%;"/>

    <c:if test="${not empty INFO}">
        <div class="alert alert-danger">
            <strong><c:out value="${INFO}"/></strong>
        </div>
    </c:if>

    <div class="row">
        <div class="col-sm-3">
            <div class="alert alert-${LED == true eq false ? 'warning': 'success'}">
                <strong>Lights <c:out value="${LED == true eq false ? 'Off': 'On'}"/></strong>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="alert alert-${HEATING == true eq false ? 'warning': 'success'}">
                <strong>Heating <c:out value="${HEATING == true eq false ? 'Off': 'On'}"/></strong>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="alert alert-${CIRCULATING == true eq false ? 'warning': 'success'}">
                <strong>Circulation <c:out value="${CIRCULATING == true eq false ? 'Off': 'On'}"/></strong>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="alert alert-${DEBUG == true eq false ? 'warning': 'success'}">
                <strong>Debug <c:out value="${DEBUG == true eq false ? 'Off': 'On'}"/></strong>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3">
            <div class="alert alert-info">
                Tub Temperature is <strong>${RETURN_TEMP}</strong>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="alert alert-info">
                Heater Temperature is <strong>${OVER_TEMP}</strong>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="alert alert-danger">
                Desired Temperature is <strong>${RETURN_TEMP_LIMIT}</strong>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="alert alert-danger">
                Heater Temperature limit is <strong>${OVER_TEMP_LIMIT}</strong>
            </div>
        </div>


        <div class="col-sm-2" id="settingsButton">
            <a class="btn btn-success" role="button" onclick="showSettings()">Change Settings</a>
        </div>

        <div id="settingsDiv">
            <div class="row">
                <div class="col-lg-4">
                    <div class="form-group">
                        <label for="returnTemp">Desired Temperature</label>
                        <input type="number" class="form-control" id="returnTemp" aria-describedby="returnTempHelp" value="${RETURN_TEMP_LIMIT}">
                        <small id="returnTempHelp" class="form-text text-muted">Temperature has to be between 6-45</small>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-4">
                    <div class="form-group">
                        <label for="overTemp">Max Heater Temperature </label>
                        <input type="number" class="form-control" id="overTemp" aria-describedby="overTempHelp" value="${OVER_TEMP_LIMIT}">
                        <small id="overTempHelp" class="form-text text-muted">Temperature has to be less than 60</small>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-4">
                    <div class="form-group">
                        <label for="timeCycle">Circulation Timer Cycle </label>
                        <input type="number" class="form-control" id="timeCycle" aria-describedby="timeCycleHelp" value="${TIMER_CYCLE}">
                        <small id="timeCycleHelp" class="form-text text-muted">Time has to be between 5 - 120.</small>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2">
                    <a href="#" id="lightswitch" onclick="lightsFlip()" class="btn btn-<c:out value="${LED == true eq false ? 'danger': 'success'}"/>" role="button">Lights: <c:out value="${LED == true eq false ? 'Off': 'On'}"/></a>
                </div>

                <div class="col-sm-2">
                    <a href="#" id="debugswitch" onclick="debugFlip()" class="btn btn-<c:out value="${DEBUG == true eq false ? 'danger': 'success'}"/>" role="button">Debug: <c:out value="${DEBUG == true eq false ? 'Off': 'On'}"/></a>
                </div>

                <div class="col-sm-2">
                    <a href="" id="test" class="btn btn-success" role="button" onclick="sendReturnTemp()">Update Settings</a>
                </div>
            </div>
        </div>






    </div>
</div>

<script>

    $( document ).ready(function() {
        document.getElementById("settingsDiv").style.display = "none";
    });

    var lightsOn = ${LED};
    var debugOn = ${DEBUG};

    function showSettings() {
        document.getElementById("settingsDiv").style.display = "block";
        document.getElementById("settingsButton").style.display = "none";
    }

    function lightsFlip() {

        if(lightsOn) {
            document.getElementById("lightswitch").innerHTML = "Lights: Off";
            document.getElementById("lightswitch").classList.add('btn-danger');
            document.getElementById("lightswitch").classList.remove('btn-success');
        }
        else {
            document.getElementById("lightswitch").innerHTML = "Lights: On";
            document.getElementById("lightswitch").classList.add('btn-success');
            document.getElementById("lightswitch").classList.remove('btn-danger');
        }
        lightsOn = !lightsOn;
    }

    // For snoopy fuckers, this is all disgusting, yeah i know. But also it doesn't feel like i give a fuck :)

    function debugFlip() {
        if(debugOn) {
            document.getElementById("debugswitch").innerHTML = "Debug: Off";
            document.getElementById("debugswitch").classList.add('btn-danger');
            document.getElementById("debugswitch").classList.remove('btn-success');
        }
        else {
            document.getElementById("debugswitch").innerHTML = "Debug: On";
            document.getElementById("debugswitch").classList.add('btn-success');
            document.getElementById("debugswitch").classList.remove('btn-danger');
        }
        debugOn = !debugOn;
    }

    function sendReturnTemp() {

        var returnTempLimit = document.getElementById('returnTemp').value;
        var overTempLimit = document.getElementById('overTemp').value;
        var timerCycle = document.getElementById('timeCycle').value;

        document.getElementById("test").href="?returnTempLimit=" + returnTempLimit +"&overTempLimit="+overTempLimit + "&turnLightOn="+lightsOn + "&debug="+debugOn + "&circulationTimeCycle="+timerCycle;
    }




</script>


