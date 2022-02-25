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
    url: '/auth/realms/cypress-testing-realm/protocol/openid-connect/token',
    method: 'POST',
    body: {
        username: user.username,
        password: user.password,
    },
  })
})