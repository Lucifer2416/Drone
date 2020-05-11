package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entities.Address;
import entities.Item;
import entities.User;
import manager.MItem;
import manager.MSignUp;
import util.Misc;
import util.Restriction;
import util.ResultValidation;
import util.Validation;

public class ActionItems {

	public static void getItems(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("items", MItem.getItems(ActionCategory.getSelectedCategory(request, response)));
	}
	public static void getItemById(int id, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("item", MItem.getItemById(id));
	}

	public static void removeItem(int id, HttpServletRequest request, HttpServletResponse response) {
		MItem.removeItemById(id);
	}

	public static boolean addItem(HttpServletRequest request, HttpServletResponse response) {
		String[] s_formParamsNeeded = { "Name", "category", "desc", "price", "serial", "stock", "isactive" },
				s_formParamsOptional = { "id" };
		String[] s_formValuesNeeded = { request.getParameter(s_formParamsNeeded[0]),
				request.getParameter(s_formParamsNeeded[1]),
				request.getParameter(s_formParamsNeeded[2]),
				request.getParameter(s_formParamsNeeded[3]),
				request.getParameter(s_formParamsNeeded[4]),
				request.getParameter(s_formParamsNeeded[5]),
				request.getParameter(s_formParamsNeeded[6]) };

		String[] s_formValueOptional = { request.getParameter(s_formParamsOptional[0]) };

		boolean isCompleted = false;

		// HashMap des données obligatoire
		HashMap<String, String> hm_formParamValue = new HashMap<String, String>();
		for (int i = 0; i < s_formValuesNeeded.length; i++) {
			hm_formParamValue.put(s_formParamsNeeded[i], s_formValuesNeeded[i]);
		}
		for (int i = 0; i < s_formValuesNeeded.length; i++) {
			
			System.out.println(s_formParamsNeeded[i]+" "+s_formValuesNeeded[i]);
		}
		// HasMap des données Optionnel
		for (int i = 0; i < s_formValueOptional.length; i++) {
			hm_formParamValue.put(s_formParamsOptional[i], s_formValueOptional[i]);
		}

//		isCompleted = validateForm(hm_formParamValue, request);

		if (isCompleted) {
			
			Item item=null;
			item.setId(-1);
			item.setName(hm_formParamValue.get("Name"));
			item.setCategory(Integer.parseInt(hm_formParamValue.get("category")));
			item.setDescription(hm_formParamValue.get("desc"));
			item.setPrice(Double.parseDouble(hm_formParamValue.get("price")));
			item.setSerial(hm_formParamValue.get("serial"));
			item.setImage("#");
			item.setActive(true);//hm_formParamValue.get("isactive"));
			
			int rep = MItem.addItem(item);
			System.out.println(rep);
			if (rep < 1) {
				isCompleted = false;
				if (rep == 0)
					request.setAttribute("error", "accountExisting");
				else if (rep == -1)
					request.setAttribute("error", "DBProblem");
			}
		}

		// HashMap pour conserver les valeurs entré par l'utilisateur (on ne le forcera
		// pas à tous réécrire)
		if (!isCompleted)
			request.setAttribute("hm_formParamValue", hm_formParamValue);

		return isCompleted;
	}

	public static boolean validateForm(HashMap<String, String> hm_formParamValue, HttpServletRequest request) {
		// On créer un HashMap pour contenir les potiennelles message d'erreurs
		HashMap<String, String> hm_fieldErrorMsg = new HashMap<String, String>();

		// Création des restriction de validation
		Restriction restrict1 = new Restriction(1, 45,
				Pattern.compile("^([a-zA-ZàéèêâïçÀÉÈÊÏÇ])+([ -][a-zA-ZàéèêâïçÀÉÈÊÏÇ]+)*"));

		Restriction restrictEmail = new Restriction(1, 45, Pattern.compile(
				"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"));
		Restriction restrictConfirmEmail = new Restriction(hm_formParamValue.get("email"));

		Restriction restrictPassword = new Restriction(8, 40,
				Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$"));
		Restriction restrictConfirmPassword = new Restriction(hm_formParamValue.get("password"));

		Restriction restrictAddr_no = new Restriction(1, 10, Pattern.compile("[a-zA-z0-9]*"));
		Restriction restrictionAddr_app = new Restriction( 1, 10, Pattern.compile("[a-zA-z0-9]*"));
		Restriction restrictionAddr_street = new Restriction(1, 45);
		Restriction restrictAddr_zip = new Restriction();

		// Création d'un objet Validation et ajout des restrictions à ce dernier
		Validation validation = new Validation(hm_formParamValue);
		validation.addRestriction("Name", restrict1);
		validation.addRestriction("category", restrictAddr_no);
		validation.addRestriction("desc", restrictionAddr_app);
		validation.addRestriction("price", restrictAddr_no);
		validation.addRestriction("serial", restrictionAddr_app);
		validation.addRestriction("stock", restrictAddr_no);

		// On conserve les résultat des tests
		ArrayList<ResultValidation> resultValidations = validation.validate();
		// On parcours les résultat des tests
		for (ResultValidation rv : resultValidations) {
			// Si le test ne passe pas, alors on ajoute un message d'erreur
			if (rv.getCode() != 0)
				System.out.println(getErrorMsg(rv));
				hm_fieldErrorMsg.put(rv.getKey(), getErrorMsg(rv));
		}
																		
		// On passe le hashMap en attribut à la requête
		request.setAttribute("hm_fieldErrorMsg", hm_fieldErrorMsg);

		return validation.isValidate();
	}

	private static String getErrorMsg(ResultValidation resValid) {
		String errorMsg = "";
		switch (resValid.getKey()) {// "category", "desc", "price", "serial", "stock","isactive"
		case "Name":
			errorMsg += getErrorMsgForName(resValid);
			break;
		case "category":
			errorMsg += getErrorMsgForCategory(resValid);
			break;
		case "desc":
			errorMsg += getErrorMsgForDesc(resValid);
			break;
		case "price":
			errorMsg += getErrorMsgForPrice(resValid);
			break;
		case "serial":
			errorMsg += getErrorMsgForSerial(resValid);
			break;
		case "stock":
			errorMsg += getErrorMsgForStock(resValid);
			break;
		}
		return errorMsg;
	}

	private static String getErrorMsgForName(ResultValidation resValid) {
		String errorMsg = "";
		Restriction restriction = resValid.getRestriction();

		switch (resValid.getCode()) {
		case 1:
			errorMsg += "You must fill in the name field. \n";
			break;
		case 2:
		case 3:
			errorMsg += "You must enter between " + restriction.getMinLength() + " and " + restriction.getMaxLength()
					+ " Character(s).\n";
			break;
		case 4:
			errorMsg += "Letter only, no numbers or special characters. Space and hyphen accepted, unless they have at the beginning or at the end of the name";
			break;
		case 5:
			break;
		}

		return errorMsg;
	}

	private static String getErrorMsgForCategory(ResultValidation resValid) {
		String errorMsg = "";
		Restriction restriction = resValid.getRestriction();

		switch (resValid.getCode()) {
		case 1:
			errorMsg += "You must fill in the first name field.\n";
		case 2:
		case 3:
			errorMsg += "You must enter between " + restriction.getMinLength() + " and " + restriction.getMaxLength()
					+ " Character(s).\n";
		case 4:
			errorMsg += "Lettre seulement, pas de chiffre ou de Character spéciaux. Espace and trait d'union accepté, sauf s'ils ont au début ou à la fin du nom)";
			break;
		case 5:
			break;
		}

		return errorMsg;
	}

	private static String getErrorMsgForDesc(ResultValidation resValid) {
		String errorMsg = "";
		Restriction restriction = resValid.getRestriction();

		switch (resValid.getCode()) {
		case 1:
			errorMsg += "You must fill in the email field.\n";
		case 2:
		case 3:
			errorMsg += "You must enter between " + restriction.getMinLength() + " and " + restriction.getMaxLength()
					+ " Character(s).\n";
		case 4:
			errorMsg += "Please enter a valid email address";
		case 5:
			break;
		}

		return errorMsg;
	}

	private static String getErrorMsgForPrice(ResultValidation resValid) {
		String errorMsg = "";

		switch (resValid.getCode()) {
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

	private static String getErrorMsgForSerial(ResultValidation resValid) {
		String errorMsg = "";

		switch (resValid.getCode()) {
		case 1:
			errorMsg += "You must fill in the confirmation field for the password.\n";
			break;
		case 2:
//		case 3:
		case 4:
		case 5:
			errorMsg += "The password does not match with the confirmation of the password.";
			break;
		}

		return errorMsg;
	}

	private static String getErrorMsgForStock(ResultValidation resValid) {
		String errorMsg = "";
		Restriction restriction = resValid.getRestriction();

		switch (resValid.getCode()) {
		case 1:
			errorMsg += "You must fill in field no.\n";
		case 2:
		case 3:
			errorMsg += "You must enter between " + restriction.getMinLength() + " and " + restriction.getMaxLength()
					+ " Character(s).\n";
		case 4:
			errorMsg += "The field accepts only numbers or letters without spaces";
		case 5:
			break;
		}

		return errorMsg;
	}


}
