lmsApp.factory("lmsFactory", function($http) {
	return {
		getAll : function(url) {
			var listObjs = {};
			return $http.get(url).success(function(data) {
				listObjs = data;
			}).then(function() {
				return listObjs;
			})
		},
		saveObject : function(url, obj) {
			var listObjs = {};
			return $http.post(url, obj).success(function(data) {
				listObjs = data;
			}).then(function() {
				return listObjs;
			})
		}
	}
})

lmsApp.factory("pageDataPass", function() {
	var savedData = {}
	 function set(data) {
	   savedData = data;
	 }
	 function get() {
	  return savedData;
	 }

	 return {
	  set: set,
	  get: get
	 }
})