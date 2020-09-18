import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.File
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

fun Document.save(xmlFile: File) =
    TransformerFactory.newInstance().newTransformer().let {
        it.setOutputProperty(OutputKeys.INDENT, "yes");
        it.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        it.transform(DOMSource(this), StreamResult(xmlFile))
    }

fun Node.appendChild(elemen: E): Element {
    val owner = if(this is Document) this else this.ownerDocument!!
    val newElement = owner.createElement(elemen.name)
    this.appendChild(newElement)
    for (attr in elemen.attributes) {
        if (attr.name.isNotBlank() && attr.value.isNotBlank()) {
            newElement.setAttribute(attr.name, attr.value)
        }
    }

    for (nestedElement in elemen.elements) {
        newElement.appendChild(nestedElement)
    }

    return newElement
}

data class E(val name: String, val elements: Sequence<E>) {
    private val _attrs = mutableListOf<A>()

    public val attributes get() = _attrs.asSequence()

    constructor(name: String, vararg nestedElements: E)
            : this(name, nestedElements.asSequence())

    fun a(name: String, value: String):E {
        _attrs.add(A(name, value))
        return this
    }
}

data class A(val name: String, val value: String)
