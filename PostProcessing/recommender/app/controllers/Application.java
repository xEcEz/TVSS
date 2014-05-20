package controllers;

import java.util.List;
import java.util.Map;

import models.UserShow;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.Controller;
import play.mvc.Result;
import controllers.TVShow;

public class Application extends Controller {

	public static Form<UserShow> showForm = Form.form(UserShow.class);
	public static List<TVShow> choices;
	public static List<String> choicesNames;
	public static MatrixManager mm;
	public static boolean loaded = false;


	public static Result index() {
		if(!loaded){
			mm = new MatrixManager();
			choices = mm.getListOfTVShow();
			choicesNames = mm.getListOfTitle();
			loaded = true;
		}

		return ok(views.html.index.render(UserShow.all(), showForm, choices));
	}

	public static Result newShow() {
		Form<UserShow> filledForm = showForm.bindFromRequest();

		if (!filledForm.hasErrors() && !choicesNames.contains(filledForm.get().label)) {
			ValidationError e = new ValidationError("label",
						"Please enter a correct TV show name" );
			filledForm.reject(e);
		}

		if (filledForm.hasErrors()) {
			return badRequest(views.html.index.render(UserShow.all(), filledForm,
					choices));
		} else {
			UserShow.create(filledForm.get());
			return redirect(routes.Application.index());
		}
	}

	public static Result deleteShow(Long id) {
		UserShow.delete(id);
		return redirect(routes.Application.index());
	}

	public static Result recommend()
	{
		List<TvShowSimPair> result = Recommender.start(UserShow.all());
		List<TvShowSimPair> resultIMDB = Recommender.sortByRating(result);
		return ok(views.html.result.render(result,resultIMDB));
	}

}
