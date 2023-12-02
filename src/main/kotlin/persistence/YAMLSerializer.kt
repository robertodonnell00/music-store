package persistence

import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception

class YAMLSerializer(private val file: File) : Serializer {

    @Throws(Exception::class)
    override fun read(): Any {
        val yaml = Yaml()
        val read = FileReader(file)
        val obj = yaml.load(read) as Any
        read.close()
        return obj
    }

    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val yaml = Yaml()
        val outputStream = FileWriter(file)
        yaml.dump(obj, outputStream)
        outputStream.close()
    }
}
