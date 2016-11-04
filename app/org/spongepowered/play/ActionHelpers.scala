package org.spongepowered.play

import com.google.common.base.Preconditions._
import play.api.data.Form
import play.api.mvc.{Call, Result}
import play.api.mvc.Results._

/**
  * A helper class for some common functions of controllers.
  */
trait ActionHelpers {

  /**
    * Redirects to the specified call with the first error of the specified
    * Form.
    *
    * @param call Call to redirect to
    * @param form Form with error
    * @return     Redirect to call
    */
  def FormError(call: Call, form: Form[_]) = {
    checkNotNull(call, "null call", "")
    checkNotNull(form, "null form", "")
    checkArgument(form.errors.nonEmpty, "no errors", "")
    val firstError = form.errors.head
    Redirect(call).flashing("error" -> (firstError.message + '.' + firstError.key))
  }

  implicit final class SpongeResult(result: Result) {

    /**
      * Adds an error message to the result.
      *
      * @param error  Error message
      * @return       Result with error
      */
    def withError(error: String) = result.flashing("error" -> error)

    /**
      * Adds a success message to the result.
      *
      * @param message  Success message
      * @return         Result with message
      */
    def withSuccess(message: String) = result.flashing("success" -> message)

  }

}
