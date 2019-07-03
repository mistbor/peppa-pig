package contracts.provider

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url '/test?name=hello'
    }
    response {
        status 200
        body([
                "id"  : $(producer(regex('[0-9]{1,3}'))),
                "name": 'hello'
        ])
        headers {
            header('Content-Type': 'application/json;charset=UTF-8')
        }
    }
}
