package com.modelorama.scanner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.modelorama.scannerlibrary.ScannerUtility

class ScannerFragment : Fragment() {

    private var scannerUtility: ScannerUtility? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scannerUtility = ScannerUtility(context)

        var button: Button = view.findViewById(R.id.buttonScan)

        button.setOnClickListener {
            scannerUtility?.startScan { isSuccessful, barcode, _ ->
                if (isSuccessful) {
                    if (barcode.isNotBlank() && barcode.length > 4) {
                        Log.d("SCANNER_APP", "BARCODE: $barcode")
                    } else {
                        Log.d("SCANNER_APP", "NO BARCODE FOUND")
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        scannerUtility?.destroyScan()
        super.onDestroy()
    }
}
