
package ws.elearning.assessment;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.elearning.assessment package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddUserToCourse_QNAME = new QName("http://assessment.elearning.ws/", "addUserToCourse");
    private final static QName _AddUserToCourseResponse_QNAME = new QName("http://assessment.elearning.ws/", "addUserToCourseResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.elearning.assessment
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddUserToCourse }
     * 
     */
    public AddUserToCourse createAddUserToCourse() {
        return new AddUserToCourse();
    }

    /**
     * Create an instance of {@link AddUserToCourseResponse }
     * 
     */
    public AddUserToCourseResponse createAddUserToCourseResponse() {
        return new AddUserToCourseResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUserToCourse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://assessment.elearning.ws/", name = "addUserToCourse")
    public JAXBElement<AddUserToCourse> createAddUserToCourse(AddUserToCourse value) {
        return new JAXBElement<AddUserToCourse>(_AddUserToCourse_QNAME, AddUserToCourse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUserToCourseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://assessment.elearning.ws/", name = "addUserToCourseResponse")
    public JAXBElement<AddUserToCourseResponse> createAddUserToCourseResponse(AddUserToCourseResponse value) {
        return new JAXBElement<AddUserToCourseResponse>(_AddUserToCourseResponse_QNAME, AddUserToCourseResponse.class, null, value);
    }

}
