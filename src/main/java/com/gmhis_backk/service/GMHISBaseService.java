package com.gmhis_backk.service;

public abstract class GMHISBaseService<Resource, Create, Partial> {

     abstract Partial toPartial(Resource resource);

}
