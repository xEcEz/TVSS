package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class UserShow extends Model {

	@Id
	public Long id;

	@Required
	public String label;
	
	public static Finder<Long, UserShow> find = new Finder<Long, UserShow>(Long.class,
			UserShow.class);

	public static List<UserShow> all() {
		return find.all();
	}

	public static void create(UserShow show) {
		show.save("default");
	}

	public static void delete(Long id) {
		find.ref(id).delete("default");
	}

}
