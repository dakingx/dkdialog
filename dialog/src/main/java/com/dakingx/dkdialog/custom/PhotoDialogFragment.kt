package com.dakingx.dkdialog.custom

import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton
import com.dakingx.dkdialog.R
import com.dakingx.dkdialog.base.BaseDialogFragment
import com.dakingx.dkdialog.base.ViewHolder

sealed class PhotoDialogAction {
    object Capture : PhotoDialogAction()
    object Gallery : PhotoDialogAction()
    object Cancel : PhotoDialogAction()
}

interface PhotoDialogListener {
    fun onPhotoAction(action: PhotoDialogAction)
}

class PhotoDialogFragment : BaseDialogFragment() {

    init {
        fmName = "PhotoDialogFragment-${System.currentTimeMillis()}"
        layoutId = R.layout.dialog_photo
        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.WRAP_CONTENT
        gravity = Gravity.BOTTOM
        cancelOutside = true
    }

    private var listener: PhotoDialogListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listener = context as? PhotoDialogListener
    }

    override fun convertView(holder: ViewHolder, fragment: BaseDialogFragment) {
        // 拍照
        val captureBtn = holder.getView<AppCompatButton>(R.id.btn_capture)
        captureBtn.setOnClickListener {
            listener?.onPhotoAction(PhotoDialogAction.Capture)
            dismiss()
        }
        // 相册
        val galleryBtn = holder.getView<AppCompatButton>(R.id.btn_gallery)
        galleryBtn.setOnClickListener {
            listener?.onPhotoAction(PhotoDialogAction.Gallery)
            dismiss()
        }
        // 取消
        val cancelBtn = holder.getView<AppCompatButton>(R.id.btn_cancel)
        cancelBtn.setOnClickListener {
            listener?.onPhotoAction(PhotoDialogAction.Cancel)
            dismiss()
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        listener?.onPhotoAction(PhotoDialogAction.Cancel)
    }
}
