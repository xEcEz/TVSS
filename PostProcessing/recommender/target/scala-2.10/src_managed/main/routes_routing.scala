// @SOURCE:/Users/nilsbouchardom/Documents/EPFL/Master-semestre2/Big data/website/recommender/conf/routes
// @HASH:91e2f31f48249dc4f5aab942940d29df8f97a7d8
// @DATE:Tue May 20 11:07:31 CEST 2014


import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString

object Routes extends Router.Routes {

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:6
private[this] lazy val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
        

// @LINE:9
private[this] lazy val controllers_Application_index1 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("index"))))
        

// @LINE:10
private[this] lazy val controllers_Application_newShow2 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("index"))))
        

// @LINE:11
private[this] lazy val controllers_Application_deleteShow3 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("UserShow/"),DynamicPart("id", """[^/]+""",true),StaticPart("/delete"))))
        

// @LINE:12
private[this] lazy val controllers_Application_recommend4 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("result"))))
        

// @LINE:16
private[this] lazy val controllers_Assets_at5 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
        

// @LINE:17
private[this] lazy val controllers_Assets_at6 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("libs/"),DynamicPart("file", """.+""",false))))
        

// @LINE:18
private[this] lazy val controllers_Assets_at7 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("special/"),DynamicPart("file", """.+""",false))))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """index""","""controllers.Application.index()"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """index""","""controllers.Application.newShow()"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """UserShow/$id<[^/]+>/delete""","""controllers.Application.deleteShow(id:Long)"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """result""","""controllers.Application.recommend()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """libs/$file<.+>""","""controllers.Assets.at(paths:String = "/public", file:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """special/$file<.+>""","""controllers.Assets.at(paths:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]] 
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil,"GET", """ Home page""", Routes.prefix + """"""))
   }
}
        

// @LINE:9
case controllers_Application_index1(params) => {
   call { 
        invokeHandler(controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil,"GET", """ Tasks""", Routes.prefix + """index"""))
   }
}
        

// @LINE:10
case controllers_Application_newShow2(params) => {
   call { 
        invokeHandler(controllers.Application.newShow(), HandlerDef(this, "controllers.Application", "newShow", Nil,"POST", """""", Routes.prefix + """index"""))
   }
}
        

// @LINE:11
case controllers_Application_deleteShow3(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(controllers.Application.deleteShow(id), HandlerDef(this, "controllers.Application", "deleteShow", Seq(classOf[Long]),"POST", """""", Routes.prefix + """UserShow/$id<[^/]+>/delete"""))
   }
}
        

// @LINE:12
case controllers_Application_recommend4(params) => {
   call { 
        invokeHandler(controllers.Application.recommend(), HandlerDef(this, "controllers.Application", "recommend", Nil,"POST", """""", Routes.prefix + """result"""))
   }
}
        

// @LINE:16
case controllers_Assets_at5(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
   }
}
        

// @LINE:17
case controllers_Assets_at6(params) => {
   call(Param[String]("paths", Right("/public")), params.fromPath[String]("file", None)) { (paths, file) =>
        invokeHandler(controllers.Assets.at(paths, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """""", Routes.prefix + """libs/$file<.+>"""))
   }
}
        

// @LINE:18
case controllers_Assets_at7(params) => {
   call(Param[String]("paths", Right("/public")), params.fromPath[String]("file", None)) { (paths, file) =>
        invokeHandler(controllers.Assets.at(paths, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """""", Routes.prefix + """special/$file<.+>"""))
   }
}
        
}

}
     