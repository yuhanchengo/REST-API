package rest.resource;

import java.util.Collection;
import java.util.stream.Collectors;

// convert domain to resource
public abstract class ResourceAssembler<DomainType, ResourceType> {
	
	public abstract ResourceType toResource(DomainType domainObj);
	
	public Collection<ResourceType> allToResource(Collection<DomainType> domainObjs){
		return domainObjs.stream().map(domObj -> toResource(domObj)).collect(Collectors.toList());
	}
}
