// @SOURCE:/Users/nilsbouchardom/Documents/EPFL/Master-semestre2/Big data/website/recommender/conf/routes
// @HASH:91e2f31f48249dc4f5aab942940d29df8f97a7d8
// @DATE:Tue May 20 11:07:31 CEST 2014

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString


// @LINE:18
// @LINE:17
// @LINE:16
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
package controllers {

// @LINE:18
// @LINE:17
// @LINE:16
class ReverseAssets {
    

// @LINE:18
// @LINE:17
// @LINE:16
def at(file:String): Call = {
   (file: @unchecked) match {
// @LINE:16
case (file) if true => Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
                                                        
// @LINE:17
case (file) if true => Call("GET", _prefix + { _defaultPrefix } + "libs/" + implicitly[PathBindable[String]].unbind("file", file))
                                                        
// @LINE:18
case (file) if true => Call("GET", _prefix + { _defaultPrefix } + "special/" + implicitly[PathBindable[String]].unbind("file", file))
                                                        
   }
}
                                                
    
}
                          

// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
class ReverseApplication {
    

// @LINE:12
def recommend(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "result")
}
                                                

// @LINE:10
def newShow(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "index")
}
                                                

// @LINE:11
def deleteShow(id:Long): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "UserShow/" + implicitly[PathBindable[Long]].unbind("id", id) + "/delete")
}
                                                

// @LINE:9
// @LINE:6
def index(): Call = {
   () match {
// @LINE:6
case () if true => Call("GET", _prefix)
                                                        
// @LINE:9
case () if true => Call("GET", _prefix + { _defaultPrefix } + "index")
                                                        
   }
}
                                                
    
}
                          
}
                  


// @LINE:18
// @LINE:17
// @LINE:16
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
package controllers.javascript {

// @LINE:18
// @LINE:17
// @LINE:16
class ReverseAssets {
    

// @LINE:18
// @LINE:17
// @LINE:16
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      if (true) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
      if (true) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "libs/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
      if (true) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "special/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
      }
   """
)
                        
    
}
              

// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
class ReverseApplication {
    

// @LINE:12
def recommend : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.recommend",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "result"})
      }
   """
)
                        

// @LINE:10
def newShow : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.newShow",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "index"})
      }
   """
)
                        

// @LINE:11
def deleteShow : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.deleteShow",
   """
      function(id) {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "UserShow/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id) + "/delete"})
      }
   """
)
                        

// @LINE:9
// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      if (true) {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
      if (true) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "index"})
      }
      }
   """
)
                        
    
}
              
}
        


// @LINE:18
// @LINE:17
// @LINE:16
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
package controllers.ref {


// @LINE:18
// @LINE:17
// @LINE:16
class ReverseAssets {
    

// @LINE:16
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      
    
}
                          

// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:6
class ReverseApplication {
    

// @LINE:12
def recommend(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.recommend(), HandlerDef(this, "controllers.Application", "recommend", Seq(), "POST", """""", _prefix + """result""")
)
                      

// @LINE:10
def newShow(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.newShow(), HandlerDef(this, "controllers.Application", "newShow", Seq(), "POST", """""", _prefix + """index""")
)
                      

// @LINE:11
def deleteShow(id:Long): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.deleteShow(id), HandlerDef(this, "controllers.Application", "deleteShow", Seq(classOf[Long]), "POST", """""", _prefix + """UserShow/$id<[^/]+>/delete""")
)
                      

// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      
    
}
                          
}
        
    