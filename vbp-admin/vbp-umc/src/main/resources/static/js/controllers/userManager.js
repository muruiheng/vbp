'use strict';

/* User Manager Controller */

app.controller('userManagerController', function($http, $scope, $timeout) {
	var get_tree_url = "/resource/0101";
	
	var get_tree_node_url = "/resource/0102/";
	
	var save_node_url = "/resource/0101";
	var titles=[];
	$scope.edit_title = "资源维护";
	$scope.current_node_id = 0;
	$scope.current_node = {};
	$scope.node_changed = false;
	var getFullTitle = function (branch) {
		if (branch.id != 0) {
			getFullTitle(branch.parent);
		} else {
			return;
		}
		titles.push(branch.label);
		
	}
	var getNodeInfo = function(id) {
		$scope.get($http, get_tree_node_url+id,null,function(data) {
			$scope.node = data.node;
		}, function(message){
			
		});
	}
	$scope.node_selected = function(branch) {
		$scope.current_node_id  = branch.id;
		titles=[];
		getFullTitle(branch);
		$scope.edit_title =titles.join(">");
		
		getNodeInfo(branch.id);
		return $scope.edit_title;
	}
	
	$scope.treedata = [];
	$scope.resource_tree = {};
	var init_tree = function() {
		$scope.get($http, get_tree_url,null,function(data) {
			$scope.treedata = data.tree;
			$timeout(function() { return $scope.resource_tree.expand_all();}, 1000);
		}, function(message){
			
		});
	};
	
	init_tree();
	
   
	$scope.new_node = function() {
		if ($scope.current_node_id <= 0 ) {
			
		}
	};
    return $scope.try_adding_a_branch = function() {
      var b;
      b = tree.get_selected_branch();
      return tree.add_branch(b, {
        label: 'New Branch',
        data: {
          something: 42,
          "else": 43
        }
      });
    };
});