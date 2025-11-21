package cache

import java.io.BufferedReader
import java.io.File

class ParamsManager {
    private val paramsMap: HashMap<Int, String> = HashMap()

    fun getParam(type: ParamType): String? {
        return paramsMap[type.id]
    }

    fun loadFromPath(path: String) {
        val paramsFile = File(path, "params.txt")
        if (!paramsFile.exists()) {
            // params.txt is optional - the revision number defaults to 0 if not found
            // This is acceptable for most use cases
            return
        }
        val reader = paramsFile.bufferedReader()
        parseParams(reader)
    }

    fun parseParams(reader: BufferedReader) {
        paramsMap.clear()
        reader.forEachLine { line ->
            if (!line.contains("param=")) {
                return@forEachLine
            }
            val id = line.substringAfter("param=").substringBefore("=")
            paramsMap[id.toInt()] = line.substringAfter("param=$id=")
        }
    }
}
