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
            <div class="alert alert-${settingsDTO.lightsOn == true eq false ? 'warning': 'success'}">
                <strong>Lights <c:out value="${settingsDTO.lightsOn == true eq false ? 'Off': 'On'}"/></strong>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="alert alert-${settingsDTO.heating == true eq false ? 'warning': 'success'}">
                <strong>Heating <c:out value="${settingsDTO.heating == true eq false ? 'Off': 'On'}"/></strong>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="alert alert-${settingsDTO.circulating == true eq false ? 'warning': 'success'}">
                <strong>Circulation <c:out value="${settingsDTO.circulating == true eq false ? 'Off': 'On'}"/></strong>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="alert alert-${settingsDTO.debug == true eq false ? 'warning': 'success'}">
                <strong>Debug <c:out value="${settingsDTO.debug == true eq false ? 'Off': 'On'}"/></strong>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3">
            <div class="alert alert-info">
                Tub Temperature is <strong>${settingsDTO.returnTemp}</strong>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="alert alert-info">
                Heater Temperature is <strong>${settingsDTO.overTemp}</strong>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="alert alert-danger">
                Desired Temperature is <strong>${settingsDTO.returnTempLimit}</strong>
            </div>
        </div>

        <div class="col-sm-3">
            <div class="alert alert-danger">
                Heater Temperature limit is <strong>${settingsDTO.overTempLimit}</strong>
            </div>
        </div>


        <div class="col-sm-2" id="settingsButton">
            <a class="btn btn-success" role="button" onclick="showSettings()">Change Settings</a>
        </div>


        <form:form method="post" action="/" modelAttribute="settingsDTO" id="settingsForm" cssStyle="display: none">
            <div class="row">
                <div class="col-lg-4">
                    <div class="form-group">
                        <form:label path="returnTempLimit">Desired Temperature </form:label>
                        <form:input path="returnTempLimit" class="form-control" id="returnTempLimit" aria-describedby="returnTempHelp"/>
                        <small id="returnTempHelp" class="form-text text-muted">Temperature has to be between 6-45</small>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-4">
                    <div class="form-group">
                        <form:label path="overTempLimit">Max Heater Temperature </form:label>
                        <form:input path="overTempLimit" class="form-control" id="overTempLimit" aria-describedby="overTempHelp"/>
                        <small id="overTempHelp" class="form-text text-muted">Temperature has to be less than 60</small>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-4">
                    <div class="form-group">
                        <form:label path="circulationTimeCycle">Circulation Timer Cycle </form:label>
                        <form:input path="circulationTimeCycle" class="form-control" id="circulationTimeCycle" aria-describedby="timeCycleHelp"/>
                        <small id="timeCycleHelp" class="form-text text-muted">Time has to be between 5 - 120.</small>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2">
                    <form:hidden path="lightsOn" id="lightsOn"/>
                    <a href="#" class="btn" id="lightswitch" onclick="lightsFlip()" role="button"></a>
                </div>

                <div class="col-sm-2">
                    <form:hidden path="debug" id="debug" />
                    <a href="#" class="btn" id="debugswitch" onclick="debugFlip()" role="button"></a>
                </div>

                <div class="col-sm-2">
                    <form:button class="btn btn-success">Update Settings</form:button>
                </div>
            </div>
        </form:form>
    </div>
</div>

<script>
    // For snoopy fuckers, this is all disgusting, yeah i know. But also it doesn't feel like i give a fuck :)

    $( document ).ready(function() {
        var lightsInputElement = document.getElementById("lightsOn");
        prepareButton(!lightsInputElement.value, 'Lights', document.getElementById("lightswitch"));

        var debugInputElement = document.getElementById("debug");
        prepareButton(!debugInputElement.value, 'Debug', document.getElementById("debugswitch"));
    });

    function showSettings() {
        document.getElementById("settingsForm").style.display = "block";
        document.getElementById("settingsButton").style.display = "none";
    }

    function lightsFlip() {
        var lightsInputElement = document.getElementById("lightsOn");

        prepareButton(lightsInputElement.value == 'true', 'Lights', document.getElementById("lightswitch"));
        lightsInputElement.value = lightsInputElement.value == 'true' ? 'false' : 'true';
    }


    function debugFlip() {
        var debugInputElement = document.getElementById("debug");

        prepareButton(debugInputElement.value == 'true', 'Debug', document.getElementById("debugswitch"));

        debugInputElement.value = debugInputElement.value == 'true' ? 'false' : 'true';

        console.log("Debug is " + debugInputElement.value);
    }

    function prepareButton(isOn, textPrefix, buttonElement) {
        if(isOn) {
            buttonElement.innerHTML = textPrefix + ": Off";
            buttonElement.classList.add('btn-danger');
            buttonElement.classList.remove('btn-success');
        }
        else {
            buttonElement.innerHTML = textPrefix + ": On";
            buttonElement.classList.add('btn-success');
            buttonElement.classList.remove('btn-danger');
        }
    }

</script>


