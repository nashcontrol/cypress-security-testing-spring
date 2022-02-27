Cypress.Commands.add('login', (userType, options = {}) => {

// setup some basic types
  // and user properties
  const types = {
    admin: {
      username: 'admin',
      admin: true,
      password: 'complexpass'
    },
    user: {
      username: 'test',
      admin: false,
      password: 'pass'
    },
  }

  // grab the user
  const user = types[userType]

  cy.request({
    url: 'http://192.168.205.5:49158/auth/realms/testing-realm/protocol/openid-connect/token',
    form: true, //login with Content-Type: application/x-www-form-urlencoded, as Keycloak does not support JSON
    method: 'POST',
    body: {
        username: user.username,
        password: user.password,
        grant_type: 'password',
        client_id: 'testing-client'
    }
  })
  .its('body')
    .then((response) => {
      Cypress.env('token', response.access_token); // either this or some global var but remember that this will only work in one test case
      cy.log(response.access_token)
    })
  
})