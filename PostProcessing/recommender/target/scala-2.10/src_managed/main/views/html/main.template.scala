
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
object main extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template2[String,Html,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(title: String)(content: Html):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.32*/("""

<!DOCTYPE html>

<html>
    <head>
        <title>"""),_display_(Seq[Any](/*7.17*/title)),format.raw/*7.22*/("""</title>
        <link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*8.54*/routes/*8.60*/.Assets.at("stylesheets/mainPage.css"))),format.raw/*8.98*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(Seq[Any](/*9.59*/routes/*9.65*/.Assets.at("images/favicon.png"))),format.raw/*9.97*/("""">
        <script src=""""),_display_(Seq[Any](/*10.23*/routes/*10.29*/.Assets.at("javascripts/jquery-1.9.0.min.js"))),format.raw/*10.74*/("""" type="text/javascript"></script>
        <script type="text/javascript" language="javascript1.2">
        <!--
        	function hideAll()
        	"""),format.raw/*14.10*/("""{"""),format.raw/*14.11*/("""
        		elements = document.getElementsByClassName("hidable");
        		for(var i = 0 ; i<elements.length; i++)
        		"""),format.raw/*17.11*/("""{"""),format.raw/*17.12*/("""
        			elements[i].style.display = "none"
        		"""),format.raw/*19.11*/("""}"""),format.raw/*19.12*/("""
        	"""),format.raw/*20.10*/("""}"""),format.raw/*20.11*/("""

        	function show(x)
        	"""),format.raw/*23.10*/("""{"""),format.raw/*23.11*/("""
        		document.getElementById(x).style.display = "inline";
        	"""),format.raw/*25.10*/("""}"""),format.raw/*25.11*/("""
        	function showJust(x)
        	"""),format.raw/*27.10*/("""{"""),format.raw/*27.11*/("""
        		hideAll();
        		show(x);
        	"""),format.raw/*30.10*/("""}"""),format.raw/*30.11*/("""

        	function toPourcentage(x)
        	"""),format.raw/*33.10*/("""{"""),format.raw/*33.11*/("""
        		var rounded = Math.round(x* 10000) / 100;
        		document.write(rounded);
        	"""),format.raw/*36.10*/("""}"""),format.raw/*36.11*/("""

          function getInfo()
          """),format.raw/*39.11*/("""{"""),format.raw/*39.12*/("""
            var title = document.getElementById("textArea").value;
            var listOfTitle = document.getElementsByTagName("option");
            for(var i = 0 ; i<listOfTitle.length; i++)
            """),format.raw/*43.13*/("""{"""),format.raw/*43.14*/("""
              if (listOfTitle[i].value == title)
              """),format.raw/*45.15*/("""{"""),format.raw/*45.16*/("""
                var id = listOfTitle[i].id.toString();
                window.location = "special/results/shows/show"+id+".html"
              """),format.raw/*48.15*/("""}"""),format.raw/*48.16*/("""
            """),format.raw/*49.13*/("""}"""),format.raw/*49.14*/("""


          """),format.raw/*52.11*/("""}"""),format.raw/*52.12*/("""


        //-->
		</script>
		<style type="text/css">
		input
		"""),format.raw/*59.3*/("""{"""),format.raw/*59.4*/("""
			font-family: 'Dosis-Medium', Helvetica, sans-serif;
			color: #FFFFFF;
			background-color: transparent;
			font-size:30px;
			-webkit-border-radius: 50px;
			-moz-border-radius: 50px;
			border-radius: 10px;
		"""),format.raw/*67.3*/("""}"""),format.raw/*67.4*/("""
		body
		"""),format.raw/*69.3*/("""{"""),format.raw/*69.4*/("""
			font-family: 'Dosis-Medium', Helvetica, sans-serif;
			text-align:center;
			font-size:30px;
			background:url("""),_display_(Seq[Any](/*73.20*/routes/*73.26*/.Assets.at("images/dark-canvas.gif"))),format.raw/*73.62*/(""");
			color: #FFFFFF;
		"""),format.raw/*75.3*/("""}"""),format.raw/*75.4*/("""
		@font-face
		"""),format.raw/*77.3*/("""{"""),format.raw/*77.4*/("""
    		font-family: "Dosis-Medium";
    		src: url("""),_display_(Seq[Any](/*79.17*/routes/*79.23*/.Assets.at("Font/Dosis/Dosis-Medium.ttf"))),format.raw/*79.64*/(""");
		"""),format.raw/*80.3*/("""}"""),format.raw/*80.4*/("""
	    ul"""),format.raw/*81.8*/("""{"""),format.raw/*81.9*/("""
			margin-left: 0;
			padding-left: 0;
		"""),format.raw/*84.3*/("""}"""),format.raw/*84.4*/("""
		li """),format.raw/*85.6*/("""{"""),format.raw/*85.7*/("""
	    	list-style-type: none;
	    """),format.raw/*87.6*/("""}"""),format.raw/*87.7*/("""
	    a """),format.raw/*88.8*/("""{"""),format.raw/*88.9*/("""
		  	outline: none;
			text-decoration : none;
			color: #FFFFFF;

	    """),format.raw/*93.6*/("""}"""),format.raw/*93.7*/("""
		/******************************************************************
			MAIN PAGE
		******************************************************************/
		#mainPage
		"""),format.raw/*98.3*/("""{"""),format.raw/*98.4*/("""
			position:relative;
			top:100px;
		"""),format.raw/*101.3*/("""}"""),format.raw/*101.4*/("""
		.error
		"""),format.raw/*103.3*/("""{"""),format.raw/*103.4*/("""
			font-size:18px;
		    font-style: italic;
		"""),format.raw/*106.3*/("""}"""),format.raw/*106.4*/("""
		#arraw
		"""),format.raw/*108.3*/("""{"""),format.raw/*108.4*/("""
			position:relative;
			top:-10px;
			border:0;
			weight:70px;
			height:70px;
		"""),format.raw/*114.3*/("""}"""),format.raw/*114.4*/("""
		#cross
		"""),format.raw/*116.3*/("""{"""),format.raw/*116.4*/("""
			position:relative;
			top: -10px;
			left: 10px;
			height:30px;
			width:30px;
		"""),format.raw/*122.3*/("""}"""),format.raw/*122.4*/("""
		#loupe
		"""),format.raw/*124.3*/("""{"""),format.raw/*124.4*/("""
			position:relative;
			top: 15px;
			left: 10px;
			height:50px;
			width:50px;
		"""),format.raw/*130.3*/("""}"""),format.raw/*130.4*/("""
		/******************************************************************
			PAGE RESULT
		******************************************************************/
		#resultPage
		"""),format.raw/*135.3*/("""{"""),format.raw/*135.4*/("""
			position:relative;
			top:100px;
		"""),format.raw/*138.3*/("""}"""),format.raw/*138.4*/("""
		#sort
		"""),format.raw/*140.3*/("""{"""),format.raw/*140.4*/("""
			font-size:18px;

		"""),format.raw/*143.3*/("""}"""),format.raw/*143.4*/("""
		#goIndex
		"""),format.raw/*145.3*/("""{"""),format.raw/*145.4*/("""
			padding-top:50px;
		"""),format.raw/*147.3*/("""}"""),format.raw/*147.4*/("""
		th,td
		"""),format.raw/*149.3*/("""{"""),format.raw/*149.4*/("""
			font-size:18px;
			text-align:center;
			margin-left:auto;
			margin-right:auto;
		"""),format.raw/*154.3*/("""}"""),format.raw/*154.4*/("""
    table
    """),format.raw/*156.5*/("""{"""),format.raw/*156.6*/("""
      margin-top:50px;
      border-collapse:collapse;
      font-size:18px;
      text-align:center;
      margin-left:auto;
      margin-right:auto;
    """),format.raw/*163.5*/("""}"""),format.raw/*163.6*/("""

		.hidable
		"""),format.raw/*166.3*/("""{"""),format.raw/*166.4*/("""
      margin-top:50px;
			text-align:center;
			margin-left:auto;
			margin-right:auto;
		"""),format.raw/*171.3*/("""}"""),format.raw/*171.4*/("""

		</style>


    </head>
    <body>
		"""),_display_(Seq[Any](/*178.4*/content)),format.raw/*178.11*/("""
    </body>
</html>
"""))}
    }
    
    def render(title:String,content:Html): play.api.templates.HtmlFormat.Appendable = apply(title)(content)
    
    def f:((String) => (Html) => play.api.templates.HtmlFormat.Appendable) = (title) => (content) => apply(title)(content)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Tue May 20 11:07:31 CEST 2014
                    SOURCE: /Users/nilsbouchardom/Documents/EPFL/Master-semestre2/Big data/website/recommender/app/views/main.scala.html
                    HASH: 610baa483498036462e34a16c7b9cc8ed4f20723
                    MATRIX: 778->1|902->31|990->84|1016->89|1113->151|1127->157|1186->195|1282->256|1296->262|1349->294|1410->319|1425->325|1492->370|1670->520|1699->521|1853->647|1882->648|1967->705|1996->706|2034->716|2063->717|2128->754|2157->755|2258->828|2287->829|2355->869|2384->870|2462->920|2491->921|2565->967|2594->968|2719->1065|2748->1066|2817->1107|2846->1108|3080->1314|3109->1315|3201->1379|3230->1380|3402->1524|3431->1525|3472->1538|3501->1539|3542->1552|3571->1553|3663->1618|3691->1619|3933->1834|3961->1835|3998->1845|4026->1846|4178->1962|4193->1968|4251->2004|4302->2028|4330->2029|4373->2046|4401->2047|4489->2099|4504->2105|4567->2146|4599->2151|4627->2152|4662->2160|4690->2161|4759->2203|4787->2204|4820->2210|4848->2211|4910->2246|4938->2247|4973->2255|5001->2256|5101->2329|5129->2330|5324->2498|5352->2499|5419->2538|5448->2539|5488->2551|5517->2552|5593->2600|5622->2601|5662->2613|5691->2614|5803->2698|5832->2699|5872->2711|5901->2712|6015->2798|6044->2799|6084->2811|6113->2812|6226->2897|6255->2898|6455->3070|6484->3071|6551->3110|6580->3111|6619->3122|6648->3123|6699->3146|6728->3147|6770->3161|6799->3162|6851->3186|6880->3187|6919->3198|6948->3199|7063->3286|7092->3287|7135->3302|7164->3303|7348->3459|7377->3460|7420->3475|7449->3476|7568->3567|7597->3568|7674->3609|7704->3616
                    LINES: 26->1|29->1|35->7|35->7|36->8|36->8|36->8|37->9|37->9|37->9|38->10|38->10|38->10|42->14|42->14|45->17|45->17|47->19|47->19|48->20|48->20|51->23|51->23|53->25|53->25|55->27|55->27|58->30|58->30|61->33|61->33|64->36|64->36|67->39|67->39|71->43|71->43|73->45|73->45|76->48|76->48|77->49|77->49|80->52|80->52|87->59|87->59|95->67|95->67|97->69|97->69|101->73|101->73|101->73|103->75|103->75|105->77|105->77|107->79|107->79|107->79|108->80|108->80|109->81|109->81|112->84|112->84|113->85|113->85|115->87|115->87|116->88|116->88|121->93|121->93|126->98|126->98|129->101|129->101|131->103|131->103|134->106|134->106|136->108|136->108|142->114|142->114|144->116|144->116|150->122|150->122|152->124|152->124|158->130|158->130|163->135|163->135|166->138|166->138|168->140|168->140|171->143|171->143|173->145|173->145|175->147|175->147|177->149|177->149|182->154|182->154|184->156|184->156|191->163|191->163|194->166|194->166|199->171|199->171|206->178|206->178
                    -- GENERATED --
                */
            