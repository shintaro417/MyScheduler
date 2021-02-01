package com.example.myscheduler.flagment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myscheduler.R
import com.example.myscheduler.Schedule
import com.example.myscheduler.databinding.FragmentMainBinding
import com.example.myscheduler.databinding.FragmentRealmTestBinding
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.util.*

class RealmTestFragment : Fragment() {
    private var _binding: FragmentRealmTestBinding? = null
    private val binding get() = _binding!!
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance() //realmのオープン処理
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRealmTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mTextView = binding.textView
        val create = binding.create
        val read = binding.read
        val update = binding.update
        val delete = binding.delete

        create.setOnClickListener {
            realm.executeTransaction { db: Realm ->
                val maxId = db.where<Schedule>().max("id")
                val nextId = (maxId?.toLong() ?: 0L) + 1L
                val schedule = db.createObject<Schedule>(nextId)
                schedule.date = Date()
                schedule.title = "登録テスト"
                schedule.detail = "スケジュールの詳細情報です"
                mTextView.setText("登録しました\n" + schedule.toString())
            }
        }

        read.setOnClickListener {
            realm.executeTransaction { db: Realm ->
                val schedules = db.where<Schedule>().findAll()
                var text: String = "読込しました\n"
                for (schedule in schedules) {
                    text = text + schedule.toString()
                }
                mTextView.setText(text)
            }
        }

        update.setOnClickListener {
            realm.executeTransaction { db: Realm ->
                val schedule = db.where<Schedule>().findFirst()
                schedule?.date = Date()
                schedule?.title = "更新テスト"
                schedule?.detail = "スケジュールの修正情報です"
                mTextView.setText("更新しました\n"+schedule.toString())
            }
        }

        delete.setOnClickListener {
            realm.executeTransaction { db: Realm ->
                db.where<Schedule>()
                    .findFirst()
                    ?.deleteFromRealm()
            }
            mTextView.setText("削除しました\n")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close() //realmのクローズ処理
    }
}