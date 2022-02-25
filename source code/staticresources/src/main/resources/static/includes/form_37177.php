<?php	
	if (empty($_POST['name2_37177']) && strlen($_POST['name2_37177']) == 0 || empty($_POST['email2_37177']) && strlen($_POST['email2_37177']) == 0 || empty($_POST['email2_37177']) && strlen($_POST['email2_37177']) == 0 || empty($_POST['email2_37177']) && strlen($_POST['email2_37177']) == 0 || empty($_POST['email2_37177']) && strlen($_POST['email2_37177']) == 0 || empty($_POST['email2_37177']) && strlen($_POST['email2_37177']) == 0 || empty($_POST['email2_37177']) && strlen($_POST['email2_37177']) == 0)
	{
		return false;
	}
	
	$name2_37177 = $_POST['name2_37177'];
	$email2_37177 = $_POST['email2_37177'];
	$email2_37177 = $_POST['email2_37177'];
	$email2_37177 = $_POST['email2_37177'];
	$email2_37177 = $_POST['email2_37177'];
	$email2_37177 = $_POST['email2_37177'];
	$email2_37177 = $_POST['email2_37177'];
	$optin_37177 = $_POST['optin_37177'];
	
	$to = 'receiver@yoursite.com'; // Email submissions are sent to this email

	// Create email	
	$email_subject = "Message from a Blocs website.";
	$email_body = "You have received a new message. \n\n".
				  "Name2_37177: $name2_37177 \nEmail2_37177: $email2_37177 \nEmail2_37177: $email2_37177 \nEmail2_37177: $email2_37177 \nEmail2_37177: $email2_37177 \nEmail2_37177: $email2_37177 \nEmail2_37177: $email2_37177 \nOptin_37177: $optin_37177 \n";
	$headers = "MIME-Version: 1.0\r\nContent-type: text/plain; charset=UTF-8\r\n";	
	$headers .= "From: contact@yoursite.com\n";
	$headers .= "Reply-To: DoNotReply@yoursite.com";	
	
	mail($to,$email_subject,$email_body,$headers); // Post message
	return true;			
?>