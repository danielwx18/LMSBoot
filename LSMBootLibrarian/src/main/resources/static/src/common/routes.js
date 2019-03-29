lmsApp.config(["$routeProvider", function($routeProvider){
	return $routeProvider.when("/",{
		redirectTo: "/home"
	}).when("/home", {
		templateUrl: "home.html"
	}).when("/librarian", {
		templateUrl: "librarian.html"
	}).when("/librarianservice", {
		templateUrl: "librarianservice.html"
	}).when("/viewbranches", {
		templateUrl: "viewbranches.html"
	}).when("/showbranch", {
		templateUrl: "showsinglebranch.html"
	})
}])