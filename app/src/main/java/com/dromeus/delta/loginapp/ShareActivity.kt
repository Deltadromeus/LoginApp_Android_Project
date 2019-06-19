
package com.dromeus.delta.loginapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.dromeus.delta.loginapp.model.SocialNetwork
import com.dromeus.delta.loginapp.model.UserModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_share.*
import com.facebook.login.LoginManager
import com.twitter.sdk.android.core.TwitterCore


class ShareActivity : AppCompatActivity() {

  lateinit var user: UserModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_share)

    user = intent.extras.get("user") as UserModel
    setData(user)
  }

  fun setData(user: UserModel) {
    nameTextView.text = user.name
    userNameTextView.text =
        if (user.socialNetwork == SocialNetwork.Twitter) "@${user.userName}"
        else user.userName
    connectedWithTextView.text =
        if (user.socialNetwork == SocialNetwork.Twitter) "${connectedWithTextView.text} Twitter"
        else "${connectedWithTextView.text} Facebook"

        if (user.socialNetwork == SocialNetwork.Twitter) View.VISIBLE
        else View.GONE

    Picasso.with(this).load(user.profilePictureUrl).placeholder(R.drawable.ic_user).into(profileImageView)

  }

  fun sendToMainActivity() {
    if (user.socialNetwork == SocialNetwork.Facebook) {
      LoginManager.getInstance().logOut()
    } else {
      TwitterCore.getInstance().sessionManager.clearActiveSession()
    }
    finish()
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
  }


  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_logout, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    return when (item?.itemId) {
      R.id.action_logout -> {
        sendToMainActivity()
        return true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
