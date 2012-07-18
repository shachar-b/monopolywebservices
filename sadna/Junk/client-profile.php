<?php 
include_once("DataBase.php");
//check login
?>

<html>
	<head>
		<title> 
		Client Profile
		</title>
	</head>
	<body>

<?php
//isset - 'client_id' in $_REQUEST

if(!isset($_REQUEST['client_id'])){
	echo '<div> No Client Requested! </div>';
	
}
else {
	$db = DataBase::getInstance();
	$clientId = $_REQUEST['client_id'];
	$res = $db->query("SELECT * FROM Client WHERE ID = $clientId");
	echo $res;
	if (empty($row)) :
		echo '<div> No Client Found.. </div>';
	else :
?>
<h1>Client Profile:</h1>
<form id='client-profile'>
	<input id='phone' type='text' name='Phone' value='<?php echo $row['Phone'] ?>'/>
	<input id='first-name' type='text' name='FirstName' value='<?php echo $row['FirstName'] ?>'/>
	<input id='last-name' type='text' name='LastName' value='<?php echo $row['LastName'] ?>'/>
	<input id='user-name' type='text' name='UserName' value='<?php echo $row['UserName'] ?>'/>
	<input id='email'  type='text' name='E-mail' value='<?php echo $row['E-mail'] ?>'/>
	<input id='rank' type='text' name='Rank' value='<?php echo $row['Rank'] ?>'/>
	<input type='button' id='save-profile' value='save' />
</form>

<script>
	$(function(){
		$("form #save-profile").click(function(){
			var dataString = "phone=" + $("input#phone").val() + "&first_name=" + ;
			$.post({
				type: "POST",
				url : "save-client-profile.php",
				data: dataString,
				success : function(data){
					if(data.response == 'success'){
						
					}
					else {}
				}
			});
		});
	});
</script>
<?php endif;
} ?>
</body>
</html>