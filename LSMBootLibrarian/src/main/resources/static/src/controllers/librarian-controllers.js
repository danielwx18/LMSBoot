lmsApp.controller("librarianController", function($scope, $http, $window,
		lmsFactory, lmsConstants, $location) {
	if($location.path() === '/viewbranches'){
	lmsFactory.getAll(
			lmsConstants.LIBRARIAN_SERVER_URL + lmsConstants.GET_ALL_BRANCHES)
			.then(function(data) {
				$scope.branches = data;
			})
	}
			
	$scope.showBranch = function(branchId) {
		lmsFactory.getAll(
			lmsConstants.LIBRARIAN_SERVER_URL + lmsConstants.GET_SINGLE_BRANCH + branchId)
			.then(function(data) {
				$scope.selectedBranch = data;
				$window.location.href = "#/showbranch";
			})
	}

//	$scope.search = "";
//	$scope.selectedIndex = null;
//	$scope.selectedBranch = null;
//
//	$scope.selectPerson = function(branch, index) {
//		$scope.selectedIndex = index;
//		$scope.selectedBranch = branch;
//	};
//
//	$scope.sensitiveSearch = function(branch) {
//		if ($scope.search) {
//			return branch.branchName.indexOf($scope.search) == 0
//					|| branch.branchAddress.indexOf($scope.search) == 0;
//		}
//		return true;
//	};

})