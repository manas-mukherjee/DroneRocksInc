function DroneController1($scope, $http, $window) {
    $http.get('http://localhost:8080/activities/search/findByCustomerName?name=JoAnn').
        success(function(data) {
            $scope.dbData = data;
        });
    
    $scope.createActivity = function(minutesWorked, droneName, objURL) {
    	var taURL = "http://localhost:8080/qb/mins/"+minutesWorked+"/droneName/"+droneName;
    	console.log("taURL - "+ taURL);
    	console.log("objURL - " + objURL);
    	
        $http.get(taURL).
        success(function(data) {
            console.log('activity created in qb');
            $http.delete(objURL).
            success(function(deletedDataObjReturn) {
                console.log('record deleted from db');
                $window.location.reload();
            });
        });
    }
}

function DroneController2($scope, $http, $window) {
    $http.get('http://localhost:8080/activities/search/findByCustomerName?name=Manas').
        success(function(data) {
            $scope.dbData = data;
        });
    
    $scope.createActivity = function(minutesWorked, droneName, objURL) {
    	var taURL = "http://localhost:8080/qb/mins/"+minutesWorked+"/droneName/"+droneName;
    	console.log("taURL - "+ taURL);
    	console.log("objURL - " + objURL);
    	
        $http.get(taURL).
        success(function(data) {
            console.log('activity created in qb');
            $http.delete(objURL).
            success(function(deletedDataObjReturn) {
                console.log('record deleted from db');
                $window.location.reload();
            });
        });
    }
}