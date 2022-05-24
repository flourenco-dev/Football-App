package com.football.ui.news

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.football.model.News
import com.football.ui.navigation.Navigator
import com.football.R
import com.football.databinding.ActivityMyNewsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyNewsActivity : AppCompatActivity(), NewsAdapter.Listener {

    private lateinit var binding: ActivityMyNewsBinding
    private val newsViewModel: NewsViewModel by viewModel()
    private var loadingDialog: Dialog? = null
    private lateinit var myAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
        setupObservers()
    }

    private fun setupUi() {
        myAdapter = NewsAdapter(this)
        with(binding.myNewsRecyclerView) {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(this@MyNewsActivity)
        }
    }

    private fun setupObservers() {
        newsViewModel.showLoadingObservable.observe(this) {
            showLoading()
        }
        newsViewModel.hideLoadingObservable.observe(this) {
            hideLoading()
        }
        newsViewModel.requestErrorObservable.observe(this) {
            showRequestErrorDialog()
        }
        newsViewModel.newsResultObservable.observe(this) {
            myAdapter.setNewsItems(it)
        }
    }

    private fun showLoading() {
        loadingDialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.dialog_loading)
        }
        loadingDialog?.show()
    }

    private fun hideLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun showRequestErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.request_error_dialog_title))
            .setMessage(getString(R.string.request_error_dialog_message))
            .setPositiveButton(getString(R.string.button_ok_label)) { dialog, _ ->
                dialog.dismiss()
                newsViewModel.fetchNews()
            }
            .show()
    }

    override fun onResume() {
        super.onResume()
        newsViewModel.fetchNews()
    }

    override fun onNewsClicked(news: News) {
        Navigator.openNews(context = this, newsLink = news.newsLink)
    }
}
