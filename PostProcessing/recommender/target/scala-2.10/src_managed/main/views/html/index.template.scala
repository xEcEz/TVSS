
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
object index extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template3[List[UserShow],Form[UserShow],List[TVShow],play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(shows: List[UserShow], showForm: Form[UserShow], choices: List[TVShow]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._


Seq[Any](format.raw/*1.74*/("""

"""),format.raw/*4.1*/("""

"""),_display_(Seq[Any](/*6.2*/main("TV show")/*6.17*/ {_display_(Seq[Any](format.raw/*6.19*/("""


<div id="mainPage">

  <datalist id="characters">
	  """),_display_(Seq[Any](/*12.5*/for(s <- choices) yield /*12.22*/ {_display_(Seq[Any](format.raw/*12.24*/("""
	   <option id=""""),_display_(Seq[Any](/*13.18*/s/*13.19*/.getId())),format.raw/*13.27*/("""" value=""""),_display_(Seq[Any](/*13.37*/s/*13.38*/.getName())),format.raw/*13.48*/("""">
	  """)))})),format.raw/*14.5*/("""
  </datalist>

	<div id ="header">
  		Select one or more TV show you like.
	</div>

    """),_display_(Seq[Any](/*21.6*/form(routes.Application.newShow())/*21.40*/ {_display_(Seq[Any](format.raw/*21.42*/("""

        <!-- """),_display_(Seq[Any](/*23.15*/inputText(showForm("label")))),format.raw/*23.43*/("""  -->
		"""),_display_(Seq[Any](/*24.4*/input(showForm("label"), '_label->None, '_showConstraints -> false)/*24.71*/ { (id, name, value, args) =>_display_(Seq[Any](format.raw/*24.100*/("""
		    <input type="select" name="label" id="textArea" list="characters" """),_display_(Seq[Any](/*25.74*/toHtmlArgs(args))),format.raw/*25.90*/(""">
    		<input id="arraw" src=""""),_display_(Seq[Any](/*26.31*/routes/*26.37*/.Assets.at("images/arraw.png"))),format.raw/*26.67*/("""" type=image Value=submit align="middle" >
    		<a href="#" onclick="getInfo()"><img id="loupe" src=""""),_display_(Seq[Any](/*27.61*/routes/*27.67*/.Assets.at("images/loupe.png"))),format.raw/*27.97*/("""" /></a>

		""")))})),format.raw/*29.4*/("""
    """)))})),format.raw/*30.6*/("""

    <script type="text/javascript">
      getId();
    </script>

    <h2>"""),_display_(Seq[Any](/*36.10*/shows/*36.15*/.size())),format.raw/*36.22*/(""" show(s)</h2>
    <div>
        """),_display_(Seq[Any](/*38.10*/for(show <- shows) yield /*38.28*/ {_display_(Seq[Any](format.raw/*38.30*/("""
            <div>
                """),_display_(Seq[Any](/*40.18*/form(routes.Application.deleteShow(show.id))/*40.62*/ {_display_(Seq[Any](format.raw/*40.64*/("""
	                """),_display_(Seq[Any](/*41.19*/show/*41.23*/.label)),format.raw/*41.29*/("""
                    <input id="cross" src=""""),_display_(Seq[Any](/*42.45*/routes/*42.51*/.Assets.at("images/cross.png"))),format.raw/*42.81*/("""" type=image Value=submit align="middle" >
                """)))})),format.raw/*43.18*/("""
            </div>
        """)))})),format.raw/*45.10*/("""
    </div>



    """),_display_(Seq[Any](/*50.6*/form(routes.Application.recommend())/*50.42*/ {_display_(Seq[Any](format.raw/*50.44*/("""
        <input type="submit" value="Recommend">
    """)))})),format.raw/*52.6*/("""


</div>





""")))})),format.raw/*61.2*/("""
"""))}
    }
    
    def render(shows:List[UserShow],showForm:Form[UserShow],choices:List[TVShow]): play.api.templates.HtmlFormat.Appendable = apply(shows,showForm,choices)
    
    def f:((List[UserShow],Form[UserShow],List[TVShow]) => play.api.templates.HtmlFormat.Appendable) = (shows,showForm,choices) => apply(shows,showForm,choices)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Mon May 19 14:34:43 CEST 2014
                    SOURCE: /Users/nilsbouchardom/Documents/EPFL/Master-semestre2/Big data/website/recommender/app/views/index.scala.html
                    HASH: 6fadbc8e6d40f768c615d6402355a6e36a557c7c
                    MATRIX: 810->1|992->73|1020->92|1057->95|1080->110|1119->112|1211->169|1244->186|1284->188|1338->206|1348->207|1378->215|1424->225|1434->226|1466->236|1504->243|1630->334|1673->368|1713->370|1765->386|1815->414|1859->423|1935->490|2003->519|2113->593|2151->609|2219->641|2234->647|2286->677|2425->780|2440->786|2492->816|2536->829|2573->835|2686->912|2700->917|2729->924|2798->957|2832->975|2872->977|2944->1013|2997->1057|3037->1059|3092->1078|3105->1082|3133->1088|3214->1133|3229->1139|3281->1169|3373->1229|3434->1258|3489->1278|3534->1314|3574->1316|3659->1370|3706->1386
                    LINES: 26->1|30->1|32->4|34->6|34->6|34->6|40->12|40->12|40->12|41->13|41->13|41->13|41->13|41->13|41->13|42->14|49->21|49->21|49->21|51->23|51->23|52->24|52->24|52->24|53->25|53->25|54->26|54->26|54->26|55->27|55->27|55->27|57->29|58->30|64->36|64->36|64->36|66->38|66->38|66->38|68->40|68->40|68->40|69->41|69->41|69->41|70->42|70->42|70->42|71->43|73->45|78->50|78->50|78->50|80->52|89->61
                    -- GENERATED --
                */
            