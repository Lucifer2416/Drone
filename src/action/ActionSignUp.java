package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.MSignUp;
import util.Misc;
import util.Restriction;
import util.ResultValidation;
import util.Validation;
import entities.Address;
import entities.User;

public class ActionSignUp {
	
	public static boolean signUp(HttpServletRequest request, HttpServletResponse response) {
		String[] s_formParamsNeeded = {"lastName", "firstName", "email", "confirmEmail", "password", "confirmPassword",
				 "addr_no", "addr_street", "addr_zip", "addr_city", "addr_state", "addr_country"},
				s_formParamsOptional = {"addr_appt"};
		String[] s_formValuesNeeded = {
			request.getParameter(s_formParamsNeeded[0]),
			request.getParameter(s_formParamsNeeded[1]),
			request.getParameter(s_formParamsNeeded[2]).toLowerCase(),
			request.getParameter(s_formParamsNeeded[3]).toLowerCase(),
			request.getParameter(s_formParamsNeeded[4]),
			request.getParameter(s_formParamsNeeded[5]),
			request.getParameter(s_formParamsNeeded[6]),
			request.getParameter(s_formParamsNeeded[7]),
			request.getParameter(s_formParamsNeeded[8]),
			request.getParameter(s_formParamsNeeded[9]),
			request.getParameter(s_formParamsNeeded[10]),
			request.getParameter(s_formParamsNeeded[11])};
		
		String[] s_formValueOptional = {
			request.getParameter(s_formParamsOptional[0])
		};
		
		boolean isCompleted = true;
		
		//HashMap des données obligatoire
		HashMap<String, String> hm_formParamValue = new HashMap<String, String>();
		for(int i=0; i< s_formValuesNeeded.length; i++) {
			hm_formParamValue.put(s_formParamsNeeded[i], s_formValuesNeeded[i]);
		}

		//HasMap des données Optionnel
		for(int i=0; i< s_formValueOptional.length; i++) {
			hm_formParamValue.put(s_formParamsOptional[i], s_formValueOptional[i]);
		}
		
		//Valide le formulaire et enregriste les message d'erreur dans la requête
		isCompleted = validateForm(hm_formParamValue, request);

		//On fait maintenant l'insertion des données dans la base de données
		if(isCompleted) {
			// Créer l'addresse en premier
			// Creer l'utilisateur et relie l'adresse
			User user = new User();
			Address address = new Address();
					
			//Utilisez Misc.getOrDefault pour les champs qui sont optionnel, car il se pourrait que sa retourne null
			// Par exemple si on le supprime du formulaire (en éditant la page) et il n'est pas testé plus haut
			address.setId(-1);
			address.setNo(hm_formParamValue.get("addr_no"));
			address.setAppt(Misc.getOrDefault(hm_formParamValue, "addr_appt", ""));
			address.setStreet(hm_formParamValue.get("addr_street"));
			address.setZip(hm_formParamValue.get("addr_zip"));
			address.setCity(hm_formParamValue.get("addr_city"));
			address.setState(hm_formParamValue.get("addr_state"));
			address.setCountry(hm_formParamValue.get("addr_country"));
			
			user.setId(-1);
			user.setLastName(hm_formParamValue.get("lastName"));
			user.setFirstName(hm_formParamValue.get("firstName"));
			user.setEmail(hm_formParamValue.get("email"));
			user.setPassword(hm_formParamValue.get("password"));
			user.setShipAdress(address);
			
			int rep = MSignUp.signUp(user);

			//Si une erreur est arrivé
			if(rep < 1) {
				isCompleted = false;
				if(rep == 0)
					request.setAttribute("error", "accountExisting");
				else if(rep == -1)
					request.setAttribute("error", "DBProblem");
			}
		}
		
		//HashMap pour conserver les valeurs entré par l'utilisateur (on ne le forcera pas à tous réécrire)
		if(!isCompleted)
			request.setAttribute("hm_formParamValue", hm_formParamValue);
		
		return isCompleted;
	}
	
	public static boolean validateForm(HashMap<String, String> hm_formParamValue, HttpServletRequest request) {
		//On créer un HashMap pour contenir les potiennelles message d'erreurs
		HashMap<String, String> hm_fieldErrorMsg = new HashMap<String, String>();
		
		//Création des restriction de validation
		Restriction restrict1 = new Restriction(1, 45, Pattern.compile("^([a-zA-ZàéèêâïçÀÉÈÊÏÇ])+([ -][a-zA-ZàéèêâïçÀÉÈÊÏÇ]+)*"));
		
		Restriction restrictEmail = new Restriction(1, 45, Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"));
		Restriction restrictConfirmEmail = new Restriction(hm_formParamValue.get("email"));
		
		Restriction restrictPassword = new Restriction(8, 40, Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$"));
		Restriction restrictConfirmPassword = new Restriction(hm_formParamValue.get("password"));
		
		Restriction restrictAddr_no = new Restriction(1, 10, Pattern.compile("[a-zA-z0-9]*"));
		Restriction restrictionAddr_app = new Restriction(true, 1, 10, Pattern.compile("[a-zA-z0-9]*"));
		Restriction restrictionAddr_street =  new Restriction(1, 45);
		Restriction restrictAddr_zip = new Restriction();
		
		//Création d'un objet Validation et ajout des restrictions à ce dernier
		Validation validation = new Validation(hm_formParamValue);
		validation.addRestriction("lastName", restrict1);
		validation.addRestriction("firstName", restrict1);
		validation.addRestriction("email", restrictEmail);
		validation.addRestriction("confirmEmail", restrictConfirmEmail);
		validation.addRestriction("password", restrictPassword);
		validation.addRestriction("confirmPassword", restrictConfirmPassword);
		validation.addRestriction("addr_no", restrictAddr_no);
		validation.addRestriction("addr_appt", restrictionAddr_app);
		validation.addRestriction("addr_street", restrictionAddr_street);
		validation.addRestriction("addr_zip", restrictAddr_zip);
		validation.addRestriction("addr_city", restrict1);
		validation.addRestriction("addr_state", restrict1);
		validation.addRestriction("addr_country", restrict1);
		
		//On conserve les résultat des tests
		ArrayList<ResultValidation> resultValidations = validation.validate();
		//On parcours les résultat des tests
		for(ResultValidation rv : resultValidations) {
			//Si le test ne passe pas, alors on ajoute un message d'erreur
			if(rv.getCode() != 0)
				hm_fieldErrorMsg.put(rv.getKey(), getErrorMsg(rv));
		}
		
		//On passe le hashMap en attribut à la requête
		request.setAttribute("hm_fieldErrorMsg", hm_fieldErrorMsg);

		return validation.isValidate();
	}

	private static String getErrorMsg(ResultValidation resValid) {
		String errorMsg = "";
		switch(resValid.getKey()) {
			case "lastName":
				errorMsg += getErrorMsgForLastName(resValid);
				break;
			case "firstName":
				errorMsg += getErrorMsgForFirstName(resValid);
				break;
			case "email":
				errorMsg += getErrorMsgForEmail(resValid);
				break;
			case "confirmEmail":
				errorMsg += getErrorMsgForConfirmEmail(resValid);
				break;
			case "password":
				errorMsg += getErrorMsgForPassword(resValid);
				break;
			case "confirmPassword":
				errorMsg += getErrorMsgForConfirmPassword(resValid);
				break;
			case "addr_no":
				errorMsg += getErrorMsgForAddr_no(resValid);
				break;
			case "addr_appt":
				errorMsg += getErrorMsgForAddr_appt(resValid);
				break;
			case "addr_street":
				errorMsg += getErrorMsgForAddr_street(resValid);
				break;
			case "addr_zip":
				errorMsg += getErrorMsgForAddr_zip(resValid);
				break;
			case "addr_city":
				errorMsg += getErrorMsgForAddr_city(resValid);
				break;
			case "addr_state":
				errorMsg += getErrorMsgForAddr_state(resValid);
				break;
			case "addr_country":
				errorMsg += getErrorMsgForAddr_country(resValid);
				break;
		}
		
		return errorMsg;
	}
	
	private static String getErrorMsgForLastName(ResultValidation resValid) {
		String errorMsg = "";
		Restriction restriction = resValid.getRestriction();
		
		switch(resValid.getCode()) {
			case 1:
				errorMsg += "You must fill in the name field. \n";
				break;
			case 2:
			case 3:
				errorMsg += "You must enter between "+restriction.getMinLength()+ " and "+restriction.getMaxLength()+" Character(s).\n";
				break;
			case 4:
				errorMsg += "Letter only, no numbers or special characters. Space and hyphen accepted, unless they have at the beginning or at the end of the name";
				break;
			case 5:
				break;
		}
		
		return errorMsg;
	}
	
	private static String getErrorMsgForFirstName(ResultValidation resValid) {
		String errorMsg = "";
		Restriction restriction = resValid.getRestriction();
		
		switch(resValid.getCode()) {
			case 1:
				errorMsg += "You must fill in the first name field.\n";
			case 2:
			case 3:
				errorMsg += "You must enter between "+restriction.getMinLength()+ " and "+restriction.getMaxLength()+" Character(s).\n";
			case 4:
				errorMsg += "Lettre seulement, pas de chiffre ou de Character spéciaux. Espace and trait d'union accepté, sauf s'ils ont au début ou à la fin du nom)";
				break;
			case 5:
				break;
		}
		
		return errorMsg;
	}
	
	private static String getErrorMsgForEmail(ResultValidation resValid) {
		String errorMsg = "";
		Restriction restriction = resValid.getRestriction();
		
		switch(resValid.getCode()) {
			case 1:
				errorMsg += "You must fill in the email field.\n";
			case 2:
			case 3:
				errorMsg += "You must enter between "+restriction.getMinLength()+ " and "+restriction.getMaxLength()+" Character(s).\n";
			case 4:
				errorMsg += "Please enter a valid email address";
			case 5:
				break;
		}
		
		return errorMsg;
	}
	
	private static String getErrorMsgForConfirmEmail(ResultValidation resValid) {
		String errorMsg = "";
		
		switch(resValid.getCode()) {
			case 1:
				errorMsg += "You must fill in the confirmation field for the email address.\n";
				break;
			case 2:
			case 3:
			case 4:
			case 5:
				errorMsg += "The email address does not match with the confirmation of the email address.";
				break;
		}
		
		return errorMsg;
	}
	
	private static String getErrorMsgForPassword(ResultValidation resValid) {
		String errorMsg = "";
		Restriction restriction = resValid.getRestriction();
		
		switch(resValid.getCode()) {
			case 1:
				errorMsg += "You must fill in the password field.\n";
			case 2:
			case 3:
				errorMsg += "You must enter between "+restriction.getMinLength()+ " and "+restriction.getMaxLength()+" Character(s).\n";
			case 4:
				errorMsg += "Please enter a password including at least 1 lowercase, 1 uppercase, 1 number and 1 special character.";
			case 5:
				break;
		}
		
		return errorMsg;
	}
	
	private static String getErrorMsgForConfirmPassword(ResultValidation resValid) {
		String errorMsg = "";
		
		switch(resValid.getCode()) {
			case 1:
				errorMsg += "You must fill in the confirmation field for the password.\n";
				break;
			case 2:
			case 3:
			case 4:
			case 5:
				errorMsg += "The password does not match with the confirmation of the password.";
				break;
		}
		
		return errorMsg;
	}
	
	private static String getErrorMsgForAddr_no(ResultValidation resValid) {
		String errorMsg = "";
		Restriction restriction = resValid.getRestriction();
		
		switch(resValid.getCode()) {
			case 1:
				errorMsg += "You must fill in field no.\n";
			case 2:
			case 3:
				errorMsg += "You must enter between "+restriction.getMinLength()+ " and "+restriction.getMaxLength()+" Character(s).\n";
			case 4:
				errorMsg += "The field accepts only numbers or letters without spaces";
			case 5:
				break;
		}
		
		return errorMsg;
	}
	
	private static String getErrorMsgForAddr_appt(ResultValidation resValid) {
		String errorMsg = "The field is optional, but if you enter it :\n";
		Restriction restriction = resValid.getRestriction();
		
		switch(resValid.getCode()) {
			case 1:
			case 2:
			case 3:
				errorMsg += "You must enter between "+restriction.getMinLength()+ " and "+restriction.getMaxLength()+" Character(s).\n";
			case 4:
				errorMsg += "The field accepts only numbers and letters";
			case 5:
				break;
		}
		
		return errorMsg;
	}
	
	private static String getErrorMsgForAddr_street(ResultValidation resValid) {
		String errorMsg = "";
		Restriction restriction = resValid.getRestriction();
		
		switch(resValid.getCode()) {
			case 1:
				errorMsg += "You must fill in the street field..\n";
			case 2:
			case 3:
				errorMsg += "You must enter between "+restriction.getMinLength()+ " and "+restriction.getMaxLength()+" Character(s).";
			case 4:
			case 5:
				break;
		}
		
		return errorMsg;
	}
	
	private static String getErrorMsgForAddr_zip(ResultValidation resValid) {
		String errorMsg = "";
		switch(resValid.getCode()) {
			case 1:
				errorMsg += "You must fill in the postal code field.";
			case 2:
			case 3:
			case 4:
			case 5:
				break;
		}
		
		return errorMsg;
	}
	
	private static String getErrorMsgForAddr_city(ResultValidation resValid) {
		String errorMsg = "";
		Restriction restriction = resValid.getRestriction();
		
		switch(resValid.getCode()) {
			case 1:
				errorMsg += "You must fill in the city field.\n";
			case 2:
			case 3:
				errorMsg += "You must enter between "+restriction.getMinLength()+ " and "+restriction.getMaxLength()+" Character(s).\n";
			case 4:
				errorMsg += "Only letters and character spacing is accepted.";
			case 5:
				break;
		}
		
		return errorMsg;
	}
	
	private static String getErrorMsgForAddr_state(ResultValidation resValid) {
		String errorMsg = "";
		Restriction restriction = resValid.getRestriction();
		
		switch(resValid.getCode()) {
			case 1:
				errorMsg += "You must fill in the province field.\n";
			case 2:
			case 3:
				errorMsg += "You must enter between "+restriction.getMinLength()+ " and "+restriction.getMaxLength()+" Character(s).\n";
			case 4:
				errorMsg += "Only letters and character spacing is accepted.";
			case 5:
				break;
		}
		
		return errorMsg;
	}
	
	private static String getErrorMsgForAddr_country(ResultValidation resValid) {
		String errorMsg = "";
		Restriction restriction = resValid.getRestriction();
		
		switch(resValid.getCode()) {
			case 1:
				errorMsg += "You must fill in the country field.\n";
			case 2:
			case 3:
				errorMsg += "You must enter between "+restriction.getMinLength()+ " and "+restriction.getMaxLength()+" Character(s).\n";
			case 4:
				errorMsg += "Only letters and character spacing is accepted.";
			case 5:
				break;
		}
		
		return errorMsg;
	}
}
