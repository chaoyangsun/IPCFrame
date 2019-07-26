// IIPCService.aidl
package com.scy.component.mylibrary;

// Declare any non-default types here with import statements
import com.scy.component.mylibrary.model.Response;
import com.scy.component.mylibrary.model.Request;
interface IIPCService {
        Response send(in Request request);
}
