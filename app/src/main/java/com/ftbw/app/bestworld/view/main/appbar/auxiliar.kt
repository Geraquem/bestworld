package com.ftbw.app.bestworld.view.main.appbar

class auxiliar {


//    private val openPostActivity =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            when (result.resultCode) {
//                EventCommon.LOGIN_ACTIVITY_REQUEST_CODE -> {
//                    if (result.data?.getBooleanExtra("register", false) == true) {
//                        openActivity(RegisterActivity::class.java)
//                    } else {
//                        presenter.goToUserProfileAsMainUser(this)
//                    }
//
//                }
//                EventCommon.REGISTER_ACTIVITY_REQUEST_CODE -> {
//                    presenter.goToUserProfileAsMainUser(this)
//                }
//
//                //CREATED EVENT AND POST?????????
//            }
//        }
//
//    private fun openActivity(classToGo: Class<*>) {
//        openPostActivity.launch(Intent(this, classToGo))
//    }
//
//    override fun openUserProfileFromUsers(userKey: String) {
//        presenter.openFragment(this, UserProfileFragment(userKey))
//    }
//
//    override fun onAttachFragment(fragment: Fragment) {
//        if (fragment is UserProfileFragment) {
//            fragment.setCallBack(this)
//        }
//    }
//
//    override fun closeSession() {
//        Firebase.auth.signOut()
//        presenter.openFragment(this, EventsFragment())
//        //recreate() -> Another way
//    }
//
//    override fun closeFragmentSelector() {
//        bdg.bottomNavigation.menu.forEach { it.isEnabled = true }
//        supportFragmentManager.popBackStack()
//    }
//
//    override fun uploadPost() {
//        closeFragmentSelector()
//        openActivity(CreatePostActivity::class.java)
//    }
//
//    override fun createEvent() {
//        closeFragmentSelector()
//        openActivity(CreateEventActivity::class.java)
//    }
//
//    override fun onBackPressed() {
//        bdg.bottomNavigation.menu.forEach { it.isEnabled = true }
//        super.onBackPressed()
//        val count = supportFragmentManager.backStackEntryCount
//        if (count <= 0) {
//            finish()
//        }
//    }

}