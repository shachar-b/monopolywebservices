<?php

class Utils {
	
	public static function isInPage($page){
		$curr_page = self::getCurrentPage();
		if($page == $curr_page){
			return true;
		}
		return false;
	}
	
	public static function getCurrentPage(){
		return str_replace("/", "", $_SERVER['SCRIPT_NAME']);
	}
}

?>