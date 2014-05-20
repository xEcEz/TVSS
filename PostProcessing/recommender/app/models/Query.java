package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Query extends Model {

	@Id
	public Long id;

	@Required
	public String label;


}