
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Emploi du temps</title>
	<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/demo/demo.css">
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.1.min.js"></script>
	<script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>Editable DataGrid Demo</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip">&nbsp;</div>
		<div>Click the edit button on the right side of row to start editing.</div>
	</div>
	
	<div style="margin:10px 0">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="insert()">Insert Row</a>
	</div>
	
	<table id="tt" class="easyui-datagrid"></table>

	<script>
		var mysql = require('mysql');
		var subjects = [
		    {subjectid:'Math-Cl-01',name:'Mathématique'},
		    {subjectid:'Ar-Cl-01',name:'Langue Arabe'},
		    {subjectid:'Fr-Cl-01',name:'Français'},
		    {subjectid:'Ang-Cl-01',name:'Anglais'}
		];
		$(function(){
			$('#tt').datagrid({
				title:'Editable DataGrid',
				iconCls:'icon-edit',
				width:660,
				height:250,
				singleSelect:true,
				idField:'itemid',
				url:'assets/data/datagrid_data.json',
				columns:[[
					{field:'dayid',title:'Day',width:60},
					{field:'seance1',title:'08:00',width:100,
						formatter:function(value,row){
							return row.subjectname || value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'subjectid',
								textField:'name',
								data:subjects,
								required:true
							}
						}
					},
					{field:'seance2',title:'10:00',width:100,
						formatter:function(value,row){
							return row.subjectname || value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'subjectid',
								textField:'name',
								data:subjects,
								required:true
							}
						}
					},
					{field:'seance3',title:'14:00',width:100,
						formatter:function(value,row){
							return row.subjectname || value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'subjectid',
								textField:'name',
								data:subjects,
								required:true
							}
						}
					},
					{field:'seance4',title:'16:00',width:100,
						formatter:function(value,row){
							return row.subjectname || value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'subjectid',
								textField:'name',
								data:subjects,
								required:true
							}
						}
					},
					{field:'action',title:'Action',width:80,align:'center',
						formatter:function(value,row,index){
							if (row.editing){
								var s = '<a href="javascript:void(0)" onclick="saverow(this)">Save</a> ';
								var c = '<a href="javascript:void(0)" onclick="cancelrow(this)">Cancel</a>';
								return s+c;
							} else {
								var e = '<a href="javascript:void(0)" onclick="editrow(this)">Edit</a> ';
								var d = '<a href="javascript:void(0)" onclick="deleterow(this)">Delete</a>';
								return e+d;
							}
						}
					}
				]],
				onEndEdit:function(index,row){
					var ed = $(this).datagrid('getEditor', {
						index: index,
						field: 'subjectid'
					});
					row.subjectname = $(ed.target).combobox('getText');
				},
				onBeforeEdit:function(index,row){
					row.editing = true;
					$(this).datagrid('refreshRow', index);
				},
				onAfterEdit:function(index,row){
					row.editing = false;
					$(this).datagrid('refreshRow', index);
				},
				onCancelEdit:function(index,row){
					row.editing = false;
					$(this).datagrid('refreshRow', index);
				}
			});
		});
		function getRowIndex(target){
			var tr = $(target).closest('tr.datagrid-row');
			return parseInt(tr.attr('datagrid-row-index'));
		}
		function editrow(target){
			$('#tt').datagrid('beginEdit', getRowIndex(target));
		}
		function deleterow(target){
			$.messager.confirm('Confirm','Are you sure?',function(r){
				if (r){
					$('#tt').datagrid('deleteRow', getRowIndex(target));
				}
			});
		}
		function saverow(target){
			$('#tt').datagrid('endEdit', getRowIndex(target));
		}
		function cancelrow(target){
			$('#tt').datagrid('cancelEdit', getRowIndex(target));
		}
		function insert(){
			var row = $('#tt').datagrid('getSelected');
			if (row){
				var index = $('#tt').datagrid('getRowIndex', row);
			} else {
				index = 0;
			}
			$('#tt').datagrid('insertRow', {
				index: index,
				row:{
					status:'P'
				}
			});
			$('#tt').datagrid('selectRow',index);
			$('#tt').datagrid('beginEdit',index);
		}
	</script>
	
</body>
</html>