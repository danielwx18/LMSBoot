lmsApp.controller("librarianController", function($scope, $http, $window,
		lmsFactory, lmsConstants, $location, pageDataPass) {
	if($location.path() === '/viewbranches'){
	lmsFactory.getAll(
			lmsConstants.LIBRARIAN_SERVER_URL + lmsConstants.GET_ALL_BRANCHES)
			.then(function(data) {
				$scope.branches = data;
				pageDataPass.set(data);
			})
	}
	
	//var id = pageDataPass.get();
			
	$scope.showBranch = function(branchId) {
		lmsFactory.getAll(
			lmsConstants.LIBRARIAN_SERVER_URL + lmsConstants.GET_SINGLE_BRANCH + branchId)
			.then(function(data) {
				$scope.selectedBranch = data;
				$window.location.href = "#/showbranch";
			})
	}
	
	
	

})