
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.html._
/**/
object result extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template2[List[TvShowSimPair],List[TvShowSimPair],play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(shows: List[TvShowSimPair], shows2: List[TvShowSimPair]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._


Seq[Any](format.raw/*1.59*/("""

"""),format.raw/*4.1*/("""
"""),_display_(Seq[Any](/*5.2*/main("TV show")/*5.17*/ {_display_(Seq[Any](format.raw/*5.19*/("""

<div id="resultPage">

	<div id ="header">
  		Here are the TV shows that we recommand for you
	</div>

	<div id ="sort">

			<label for="sorting">Order by</label>
			<select name="sorting" onchange="showJust(this.value);" >
			    <option value="results1">Theme</option>
				<option value="results2">IMDB ratings</option>
		 	</select>


	</div>


    <div id="results1" class="hidable" style="display:inline;">
	    <table style="width:auto">
	    	<tr style="color:#FF7519; font-size:20px;">
	    		<td>Title</td>
	    		<td>Similarity</td>
	    		<td>IMDB Ratings</td>
	    	</tr>
	        """),_display_(Seq[Any](/*32.11*/for(show <- shows) yield /*32.29*/ {_display_(Seq[Any](format.raw/*32.31*/("""
	            <tr>
	                <td><a href=""""),_display_(Seq[Any](/*34.32*/routes/*34.38*/.Assets.at("results/shows/show"+show.getTvShow().getId()+".html"))),format.raw/*34.103*/("""">"""),_display_(Seq[Any](/*34.106*/show/*34.110*/.getTitle())),format.raw/*34.121*/("""</a> </td>
	                <td>
	                <script type="text/javascript">
						toPourcentage("""),_display_(Seq[Any](/*37.22*/show/*37.26*/.getSimilarity())),format.raw/*37.42*/(""");
        			</script>
        			%</td>
        			<td>
        				"""),_display_(Seq[Any](/*41.14*/show/*41.18*/.getTvShow().getRating())),format.raw/*41.42*/("""
        			</td>
	            </tr>
	        """)))})),format.raw/*44.11*/("""
	    </table>
    </div>

    <div id="results2" class="hidable" style="display:none;">
	    <table style="width:auto">
	    	<tr style="color:#FF7519; font-size:20px;">
	    		<td>Title</td>
	    		<td>Similarity</td>
	    		<td>IMDB Ratings</td>
	    	</tr>
	        """),_display_(Seq[Any](/*55.11*/for(show <- shows2) yield /*55.30*/ {_display_(Seq[Any](format.raw/*55.32*/("""
	            <tr>
	                <td><a href=""""),_display_(Seq[Any](/*57.32*/routes/*57.38*/.Assets.at("results/shows/show"+show.getTvShow().getId()+".html"))),format.raw/*57.103*/("""">"""),_display_(Seq[Any](/*57.106*/show/*57.110*/.getTitle())),format.raw/*57.121*/("""</a> </td>
	                <td>
	                <script type="text/javascript">
						toPourcentage("""),_display_(Seq[Any](/*60.22*/show/*60.26*/.getSimilarity())),format.raw/*60.42*/(""");
        			</script>
        			%</td>
        			<td>
        				"""),_display_(Seq[Any](/*64.14*/show/*64.18*/.getTvShow().getRating())),format.raw/*64.42*/("""
        			</td>
	            </tr>
	        """)))})),format.raw/*67.11*/("""
	    </table>
    </div>


    <div id="goIndex">

	    """),_display_(Seq[Any](/*74.7*/form(routes.Application.index())/*74.39*/ {_display_(Seq[Any](format.raw/*74.41*/("""
	        <input type="submit" value="Change TV Shows selection">
	    """)))})),format.raw/*76.7*/("""
    </div>



</div>
""")))})),format.raw/*82.2*/("""
"""))}
    }
    
    def render(shows:List[TvShowSimPair],shows2:List[TvShowSimPair]): play.api.templates.HtmlFormat.Appendable = apply(shows,shows2)
    
    def f:((List[TvShowSimPair],List[TvShowSimPair]) => play.api.templates.HtmlFormat.Appendable) = (shows,shows2) => apply(shows,shows2)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Mon May 19 14:41:38 CEST 2014
                    SOURCE: /Users/nilsbouchardom/Documents/EPFL/Master-semestre2/Big data/website/recommender/app/views/result.scala.html
                    HASH: 4c66575b27d45528647079a89e9a455c5ae10a70
                    MATRIX: 808->1|975->58|1003->77|1039->79|1062->94|1101->96|1734->693|1768->711|1808->713|1894->763|1909->769|1997->834|2037->837|2051->841|2085->852|2224->955|2237->959|2275->975|2382->1046|2395->1050|2441->1074|2520->1121|2827->1392|2862->1411|2902->1413|2988->1463|3003->1469|3091->1534|3131->1537|3145->1541|3179->1552|3318->1655|3331->1659|3369->1675|3476->1746|3489->1750|3535->1774|3614->1821|3707->1879|3748->1911|3788->1913|3891->1985|3945->2008
                    LINES: 26->1|30->1|32->4|33->5|33->5|33->5|60->32|60->32|60->32|62->34|62->34|62->34|62->34|62->34|62->34|65->37|65->37|65->37|69->41|69->41|69->41|72->44|83->55|83->55|83->55|85->57|85->57|85->57|85->57|85->57|85->57|88->60|88->60|88->60|92->64|92->64|92->64|95->67|102->74|102->74|102->74|104->76|110->82
                    -- GENERATED --
                */
            