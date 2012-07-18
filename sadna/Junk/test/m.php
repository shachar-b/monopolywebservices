<html>

<head>
	<title> m </title>
</head>

<body>

<?php
	$Info = array("Name" => "Mor" , "add"=> "toto");
	echo "my name is: ", $Info["Name"], "<br />";

	$more = array("Number" => "14" , "letter" => "r");
	$Info = array_merge($Info, $more);
	
		
	foreach($Info as $key => $value)
	{
		echo $value, "<br />";
	}
	
	$countryStr = "Cuba,Spain,India,France,Italy";
	$randCountry = explode(",", $countryStr);
	echo $randCountry[0], " ", $randCountry[1]," ", $randCountry[2], "<br /><br />";

	$countryStr2 = implode(",", $randCountry);
	echo $countryStr2, "<br /><br />";
	
	sort($randCountry,SORT_STRING);
	foreach($randCountry as $key => $value)
	{
		echo $value, "<br />";
	}
	print_r($randCountry);
	
	echo rand(1, 300) . "<br />";
		
	
?>

</body>

</html>