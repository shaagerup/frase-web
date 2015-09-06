package example

import scala.scalajs.js
import js.annotation.JSExport
import org.scalajs.dom

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react._


import it.vigtig.lambda._

object ScalaJSExample extends js.JSApp with InterpreterLike {
  def main(): Unit = {


    val mountNode = dom.document.getElementById("playground")

    val TodoList = ReactComponentB[List[String]]("TodoList")
      .render(props => {
        def createItem(itemText: String) = <.li(itemText)
        <.ul(props map createItem)
      })
      .build

    case class State(res: Option[List[Term]], text: String)

    class Backend($: BackendScope[Unit, State]) {
      def onChange(e: ReactEventI) =
        $.modState(_.copy(text = e.target.value))
      def handleSubmit(e: ReactEventI) = {
        e.preventDefault()
        $.modState(s => s.copy(res = interpretProgram(s.text + "\n")))
      }
    }

    val TodoApp = ReactComponentB[Unit]("TodoApp")
      .initialState(State(None, ""))
      .backend(new Backend(_))
      .render((_,S,B) =>
        <.div(
          <.h3("Type your program below"),
          TodoList(S.res.getOrElse(Nil).map(_.toString)),
          <.form(^.onSubmit ==> B.handleSubmit,
            <.textarea(^.rows := 10, ^.cols :=80, ^.onChange ==> B.onChange),
            <.button("Execute")
          )
        )
      ).buildU

    React.render(TodoApp(), mountNode)
  }

  /** Computes the square of an integer.
   *  This demonstrates unit testing.
   */
  def square(x: Int): Int = x*x
  
  def onTextChange(e: ReactEventI): ReactEventI= {
    println("Value received = " + e.target.value)
    e
  }
}
