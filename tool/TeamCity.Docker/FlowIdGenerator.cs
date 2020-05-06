using System;
using JetBrains.TeamCity.ServiceMessages.Write.Special;
// ReSharper disable ClassNeverInstantiated.Global

namespace TeamCity.Docker
{
    internal class FlowIdGenerator : IFlowIdGenerator
    {
        public string NewFlowId() => Guid.NewGuid().ToString().Replace("-", string.Empty);
    }

}
